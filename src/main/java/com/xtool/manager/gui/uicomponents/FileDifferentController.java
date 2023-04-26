package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.xtool.manager.utils.DiffHandleUtils;
import io.datafx.controller.ViewController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ViewController(value = "/fxml/ui/FileDifferent.fxml", title = "文本对比")
public class FileDifferentController  extends Application{
    @FXML
    private TextArea originalArea;
    @FXML
    private TextArea finalArea;
    @FXML
    private JFXButton compared;

    @PostConstruct
    public void init() {

        //对比按钮事件
        compared.setOnAction(action -> {
            String originalText = originalArea.getText();
            String finalText = finalArea.getText();
            List<String> originalList = Arrays.stream(originalText.split("\n")).collect(Collectors.toList());
            List<String> finalList = Arrays.stream(finalText.split("\n")).collect(Collectors.toList());

            String htmlFilePath = "C:\\tmp\\";
            String htmlFileName = "diff.html";

            //对比后获得不同点
            List<String> diffString = DiffHandleUtils.diffString(originalList, finalList);
            //生成一个diff.html文件
            DiffHandleUtils.generateDiffHtml(diffString, htmlFilePath );
            //拷贝css和js文件到htmlFilePath目录下
            copyFile(htmlFilePath, "diff2html.min.css");
            copyFile(htmlFilePath, "diff2html-ui.min.js");
            copyFile(htmlFilePath, "github.min.css");

            HostServices host = getHostServices();
            host.showDocument(htmlFilePath + htmlFileName);
        });

    }

    public void copyFile(String filePath, String fileName) {
        String filePthTemp = filePath + fileName;
        File file = new File(filePthTemp);
        //文件不存在就复制过去
        if (!file.exists()) {
            InputStream inputStream = FileDifferentController.class.getClassLoader().getResourceAsStream("filediff/" + fileName);
            try {
                FileUtils.copyInputStreamToFile(inputStream, new File(filePath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void start(Stage stage) throws Exception {
    }
}
