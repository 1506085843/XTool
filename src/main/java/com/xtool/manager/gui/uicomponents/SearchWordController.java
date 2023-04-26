package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
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
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@ViewController(value = "/fxml/ui/SearchWord.fxml", title = "多文件中搜索")
public class SearchWordController {

    @FXML
    private TextField direInput;

    @FXML
    private JFXButton direButton;

    @FXML
    private JFXToggleButton containChildenDire;


    @FXML
    private TextField searchWordInput;

    @FXML
    private JFXButton search;

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

        //是否选择子文件开关
        containChildenDire.setOnAction(action -> {
            boolean open = containChildenDire.isSelected();
            containChildenDire.setSelected(containChildenDire.isSelected());
        });

        //搜索按钮事件
        search.setOnAction(action -> {
            String dir = direInput.getText();
            String searchWord = searchWordInput.getText();
            if (dir.trim().isEmpty() || searchWord.trim().isEmpty()) {
                Platform.runLater(() -> outArea.setText("请输入搜索的文件夹或待搜索的关键字..."));
                return;
            }

            Platform.runLater(() -> outArea.setText("请稍后，搜索中..."));

            //开关打开状态
            boolean isOpen = containChildenDire.isSelected();

            //存储所有的扫描的可读文件的路径
            BlockingQueue<String> allFilesPath = new LinkedBlockingQueue<>();
            //用于判断扫描线程是否已扫描完成
            AtomicBoolean scanFinish = new AtomicBoolean(false);
            //存储所有搜索的结果<文件名，<行数，内容>>
            Map<String, Map<Integer, String>> searchResult = new LinkedHashMap<>();

            //创建一个定长线程池，初始化为2
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
            //创建一个线程用于扫描所有的可读文件路径，然后存放到 allFilesPath 里，扫描完将 scanFinish 设为 true
            System.out.println("创建扫描线程");
            fixedThreadPool.execute(() -> {
                SearchWordUtils.getAllReadFilessPath(dir, allFilesPath, isOpen);
                scanFinish.set(true);//当此线程扫描完所有线程后将scanFinish设置为true
            });
            //创建一个线程不断从 allFilesPath 里取文件路径然后搜索文件中是否含有关键字，把结果存到 searchResult 里，
            // 当 allFilesPath 为空并且 scanFinish 为true （即上面的线程扫描完了，此线程也把 allFilesPath 里的所有的文件扫描完了），就跳出searchAllFiles方法
            System.out.println("创建搜索线程");
            fixedThreadPool.execute(() -> {
                SearchWordUtils.searchAllFiles(allFilesPath, searchWord, searchResult, scanFinish, outArea);
            });

            //关闭线程池
            fixedThreadPool.shutdown();
            System.out.println("完成");

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
            direInput.setText("");
            searchWordInput.setText("");
            outArea.setText("");
        });
    }


}

