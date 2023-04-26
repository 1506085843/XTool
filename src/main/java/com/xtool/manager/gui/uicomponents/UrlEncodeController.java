package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@ViewController(value = "/fxml/ui/UrlEncode.fxml", title = "url编码解码")
public class UrlEncodeController {
    @FXML
    private TextArea inputArea;

    @FXML
    private JFXButton encode;

    @FXML
    private JFXButton decode;

    @FXML
    private JFXButton copy;

    @FXML
    private JFXButton clear;

    @FXML
    private TextArea outArea;

    @PostConstruct
    public void init() {
        //编码按钮事件
        encode.setOnAction(action -> {
            String url = inputArea.getText();
            String urlEncode = null;
            try {
                urlEncode =  URLEncoder.encode(url,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            outArea.setText(urlEncode);
        });

        //解码按钮事件
        decode.setOnAction(action -> {
            String url = inputArea.getText();
            String urlEncode = null;
            try {
                urlEncode =  URLDecoder.decode(url,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            outArea.setText(urlEncode);
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
            inputArea.setText("");
            outArea.setText("");
        });
    }
}
