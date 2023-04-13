package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ViewController(value = "/fxml/ui/TimeStamp.fxml", title = "时间戳工具")
public class TimeStampController {

    @FXML
    private TextField inputStamp;
    @FXML
    private JFXComboBox<Label> stampUnit;
    @FXML
    private JFXButton stampToTime;
    @FXML
    private TextField resultTime;

    @FXML
    private TextField inputTime;
    @FXML
    private JFXComboBox<Label> timeUnit;
    @FXML
    private JFXButton timeToStamp;
    @FXML
    private TextField resultStamp;

    @FXML
    private JFXButton currentStampButton;
    @FXML
    private TextField outCurrentStamp;
    @FXML
    private JFXComboBox<Label> currentTimeUnit;


    @PostConstruct
    public void init() {
        //设置下拉框的默认值为毫秒
        SingleSelectionModel<Label> stampUnitSelectionModel = stampUnit.getSelectionModel();
        stampUnitSelectionModel.select(0);
        stampUnit.setSelectionModel(stampUnitSelectionModel);

        SingleSelectionModel<Label> timeUnitSelectionModel = timeUnit.getSelectionModel();
        timeUnitSelectionModel.select(0);
        timeUnit.setSelectionModel(timeUnitSelectionModel);

        SingleSelectionModel<Label> currentTimeUnitSelectionModel = currentTimeUnit.getSelectionModel();
        currentTimeUnitSelectionModel.select(0);
        currentTimeUnit.setSelectionModel(currentTimeUnitSelectionModel);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowStamp = timeToStamp(now,"毫秒");
        inputStamp.setText(nowStamp);
        inputTime.setText(now.format(formate));
        outCurrentStamp.setText(nowStamp);

        //时间戳转时间按钮事件
        stampToTime.setOnAction(action -> {
            String stamp = inputStamp.getText();//输入的时间戳

            Label unitLabel = currentTimeUnit.getValue();
            String unit = unitLabel.getText();//时间戳单位

            String result = "";
            ZoneId zoneId = ZoneId.systemDefault();
            //DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if ("毫秒".equals(unit)) {
                LocalDateTime localDateTime = Instant.ofEpochMilli(Long.parseLong(stamp)).atZone(zoneId).toLocalDateTime();
                result = localDateTime.format(formate);
            } else {
                LocalDateTime localDateTime = Instant.ofEpochSecond(Long.parseLong(stamp)).atZone(zoneId).toLocalDateTime();
                result = localDateTime.format(formate);
            }
            resultTime.setText(result);
        });

        //时间转时间戳按钮事件
        timeToStamp.setOnAction(action -> {
            String time = inputTime.getText().trim();//输入的时间
            LocalDateTime localDateTime = LocalDateTime.parse(time.replace(" ", "T"));

            Label unitLabel = timeUnit.getValue();
            String unit = unitLabel.getText();//时间单位

            String result = timeToStamp(localDateTime,unit);;
            resultStamp.setText(result);
        });

        //获取当前时间戳按钮事件
        currentStampButton.setOnAction(action -> {
            Label unitLabel = currentTimeUnit.getValue();
            String unit = unitLabel.getText();//时间戳单位

            String result = timeToStamp(LocalDateTime.now(),unit);
            outCurrentStamp.setText(result);
        });

    }


    public  String timeToStamp(LocalDateTime localDateTime,String unit){
        String result = "";
        Timestamp ts = Timestamp.valueOf(localDateTime);
        if ("毫秒".equals(unit)) {
            result = String.valueOf(ts.getTime());
        } else {
            Instant instant = ts.toInstant();
            result = String.valueOf(instant.getEpochSecond());
        }
        return result;
    }
}



