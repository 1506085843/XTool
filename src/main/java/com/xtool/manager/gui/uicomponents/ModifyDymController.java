package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.xtool.manager.utils.SearchWordUtils;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ViewController(value = "/fxml/ui/ModifyDym.fxml", title = "元数据批量修改")
public class ModifyDymController {
    @FXML
    private TextField direInput;

    @FXML
    private JFXButton direButton;

    @FXML
    private TextField searchWordInput;

    @FXML
    private TextField replaceWordInput;

    @FXML
    private TextField timeStampInput;

    @FXML
    private JFXButton search;

    @FXML
    private JFXButton searchAndChange;

    @FXML
    private JFXButton copy;

    @FXML
    private JFXButton clear;

    @FXML
    private TextArea outArea;

    @PostConstruct
    public void init() {

        //选择文件夹按钮
        direButton.setOnAction(action -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("选择文件夹");
            File directory = directoryChooser.showDialog(new Stage());
            if (directory != null) {
                direInput.setText(directory.getAbsolutePath());
            }
        });

        //搜索按钮事件
        search.setOnAction(action -> {
            String dir = direInput.getText();
            String searchWord = searchWordInput.getText();
            String replaceWord = replaceWordInput.getText();
            String timeStamp = timeStampInput.getText();
            String tip = checkInput(dir, searchWord, replaceWord, timeStamp, true);
            if (null != tip) {
                Platform.runLater(() -> outArea.setText(tip));
                return;
            }
            List<String> fileType = Arrays.asList("dym");
            Map<String, Map<Integer, String>> map = SearchWordUtils.searchFiles(dir, searchWord, fileType);

            StringBuilder buffer = new StringBuilder("搜索完成，  " +dir+" 下所有元数据文件中搜索 " +searchWord + " 的结果： " + replaceWord + "\n\n");
            for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
                replaceeFileStr(m.getKey(), searchWord, replaceWord, timeStamp);
                buffer.append("文件路径： " + m.getKey() + "\n");
                for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                    buffer.append("第" + n.getKey() + "行：" + n.getValue() + "\n");
                }
                buffer.append("\n");
            }
            outArea.setText(buffer.toString());
        });


        //搜索且替换按钮事件
        searchAndChange.setOnAction(action -> {
            String dir = direInput.getText();
            String searchWord = searchWordInput.getText();
            String replaceWord = replaceWordInput.getText();
            String timeStamp = timeStampInput.getText();

            String tip = checkInput(dir, searchWord, replaceWord, timeStamp, false);
            if (null != tip) {
                Platform.runLater(() -> outArea.setText(tip));
                return;
            }

            List<String> fileType = Arrays.asList("dym");
            StringBuilder buffer = new StringBuilder("搜索和替换完成，以下文件中的  “" + searchWord + "”已被替换为 “" + replaceWord + "“\n");
            if (null != timeStamp && !timeStamp.trim().isEmpty()) {
                buffer.append("此外文件中的 ModifyDate、Version 标签中的时间戳也已经更换为你输入的时间戳" + "\n\n");
            }

            Map<String, Map<Integer, String>> map = SearchWordUtils.searchFiles(dir, searchWord, fileType);
            System.out.println("搜索完成");
            for (Map.Entry<String, Map<Integer, String>> m : map.entrySet()) {
                replaceeFileStr(m.getKey(), searchWord, replaceWord, timeStamp);
                buffer.append("文件路径： " + m.getKey() + "\n");
                for (Map.Entry<Integer, String> n : m.getValue().entrySet()) {
                    buffer.append("第" + n.getKey() + "行：" + n.getValue() + "\n");
                }
                buffer.append("\n");
            }
            outArea.setText(buffer.toString());
        });

        //复制按钮事件
        copy.setOnAction(action -> {
            String str = outArea.getText();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(str);
            clipboard.setContent(content);
        });

        //清除按钮事件
        clear.setOnAction(action -> {
            outArea.setText("");
        });
    }


    public void replaceeFileStr(String filePath, String searchWord, String replaceWord, String timeStamp) {
        FileInputStream file = null; //读取文件为字节流
        List<String> linesContents = new ArrayList<>();
        try {
            file = new FileInputStream(filePath);
            InputStreamReader in = new InputStreamReader(file, StandardCharsets.UTF_8); //字节流转化为字符流，以GBK读取防止中文乱码
            BufferedReader buf = new BufferedReader(in); //加入到缓存区
            String str = "";
            int row = 1;
            while ((str = buf.readLine()) != null) { //按行读取，到达最后一行返回null
                //替换标签中的时间戳
                if (str.contains("<ModifyDate>") && str.contains("</ModifyDate>")) {
                    str = replaceeStamp(str, "ModifyDate", timeStamp);
                }
                if (str.contains("<Version>") && str.contains("</Version>")) {
                    str = replaceeStamp(str, "Version", timeStamp);
                }
                //替换文字
                if (str.contains(searchWord)) {
                    str = str.replace(searchWord, replaceWord);
                }
                linesContents.add(str);
                row++;
            }
            buf.close();
            file.close();
            //将替换后的文件内容写入到文件中
            Path out = Paths.get(filePath);
            Files.write(out, linesContents, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String replaceeStamp(String linContent, String label, String timeStamp) {
        int start = linContent.indexOf("<" + label + ">");
        start = "ModifyDate".equals(label) ? start + 12 : start + 9;
        int end = linContent.indexOf("</" + label + ">");

        //替换标签中的文字
        StringBuilder buffer = new StringBuilder(linContent);
        buffer.replace(start, end, timeStamp);

        return buffer.toString();
    }


    public String checkInput(String dir, String searchWord, String replaceWord, String timeStamp, boolean isJustSearch) {
        if (null == dir || dir.trim().isEmpty()) {
            return "请输入元数据所在文件夹";
        }
        if (null == searchWord || searchWord.trim().isEmpty()) {
            return "请输入待搜索或待替换的字符串";
        }
        if (!isJustSearch && (null == replaceWord || replaceWord.trim().isEmpty())) {
            return "请输入替换的字符串";
        }
        if (!isJustSearch && (null == timeStamp || timeStamp.trim().isEmpty())) {
            return "请输入毫秒级时间戳";
        }
        return null;
    }

}


