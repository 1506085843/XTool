package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.xtool.manager.utils.AesUtils;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;


import javax.annotation.PostConstruct;


@ViewController(value = "/fxml/ui/AesEncode.fxml", title = "AES加密/解密")
public class AesEncodeController {
    @FXML
    private TextArea inputArea;

    @FXML
    private TextField key;

    @FXML
    private TextField vector;

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
        String defaultKey = "LX%TB*19!@#HPAZW";
        String defaultVictor = "tr3f5m7kv019p4ad";

        //编码按钮事件
        encode.setOnAction(action -> {
            String str = inputArea.getText();

            if (str.trim().isEmpty()) {
                Platform.runLater(() -> outArea.setText("请输入待加密的文本！"));
                return;
            }

            String keyStr = key.getText();
            String vectorStr = vector.getText();
            if (keyStr.isEmpty() || keyStr.length() == 16) {
                keyStr = defaultKey;
            }
            if (vectorStr.isEmpty() || vectorStr.length() == 16) {
                vectorStr = defaultVictor;
            }
            AesUtils aes = new AesUtils();
            String strEncode = null;
            try {
                strEncode = aes.encrypt(str, keyStr, vectorStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            outArea.setText(strEncode);
        });

        //解码按钮事件
        decode.setOnAction(action -> {
            String str = inputArea.getText();
            if (str.trim().isEmpty()) {
                Platform.runLater(() -> outArea.setText("请输入待解密的文本！"));
                return;
            }
            String keyStr = key.getText();
            String vectorStr = vector.getText();
            if (keyStr.isEmpty() || keyStr.length() == 16) {
                keyStr = defaultKey;
            }
            if (vectorStr.isEmpty() || vectorStr.length() == 16) {
                vectorStr = defaultVictor;
            }
            AesUtils aes = new AesUtils();
            String decodedStr = null;
            try {
                decodedStr = aes.decrypt(str, keyStr, vectorStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

