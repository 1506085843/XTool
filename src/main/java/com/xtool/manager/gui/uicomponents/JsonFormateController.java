package com.xtool.manager.gui.uicomponents;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/ui/JsonFormat.fxml", title = "AES加密/解密")
public class JsonFormateController {
    @FXML
    private TextArea inputArea;

    @FXML
    private JFXButton format;

    @FXML
    private JFXButton copy;

    @FXML
    private JFXButton clear;

    @FXML
    private TextArea outArea;

    @PostConstruct
    public void init() {

        //格式化按钮事件
        format.setOnAction(action -> {
            String str = inputArea.getText();
            str = str.trim();
            if (str.isEmpty() ) {
                return;
            }
            String formatStr = null;
            try {
                if (str.startsWith("[")) {
                    JSONArray data = JSON.parseArray(str);
                    formatStr = JSONArray.toJSONString(data, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
                } else {
                    JSONObject strJson = JSONObject.parseObject(str);
                    formatStr = JSON.toJSONString(strJson, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
                }
            }catch (Exception e){
                formatStr = "您输入的不是 json 字符串或 json 对象! 请检查您的输入。";
            }
            outArea.setText(formatStr);
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

