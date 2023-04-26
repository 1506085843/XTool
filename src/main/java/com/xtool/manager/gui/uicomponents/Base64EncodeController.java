package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import javax.annotation.PostConstruct;
import java.util.Base64;

@ViewController(value = "/fxml/ui/Base64Encode.fxml", title = "base64编码解码")
public class Base64EncodeController {
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
            String str = inputArea.getText();
            String strEncode =  Base64.getEncoder().encodeToString(str.getBytes());;
            outArea.setText(strEncode);
        });

        //解码按钮事件
        decode.setOnAction(action -> {
            String str = inputArea.getText();
            byte[] decodedBytes = Base64.getDecoder().decode(str);
            String decodedStr = new String(decodedBytes);
            outArea.setText(decodedStr);
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

