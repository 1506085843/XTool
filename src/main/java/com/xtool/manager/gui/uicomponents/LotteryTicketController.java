package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.xtool.manager.utils.TicketUtils;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ViewController(value = "/fxml/ui/LotteryTicket.fxml", title = "彩票随机生成")
public class LotteryTicketController {

    @FXML
    private JFXComboBox<Label> ticketType;

    @FXML
    private JFXComboBox<Label> ticketNumber;

    @FXML
    private JFXButton create;

    @FXML
    private JFXButton copy;

    @FXML
    private JFXButton clear;

    @FXML
    private TextArea outArea;

    @PostConstruct
    public void init() {
        //生成按钮事件
        create.setOnAction(action -> {
            Label ticketTypeLabel = ticketType.getValue();
            Label ticketNumberLabel = ticketNumber.getValue();
            if (ticketTypeLabel==null) {
                outArea.setText("请选择彩票类型");
                return;
            }
            if (ticketNumberLabel==null) {
                outArea.setText("请选择注数");
                return;
            }
            String type = ticketTypeLabel.getText();
            String numberStr = ticketNumberLabel.getText();
            int number =Integer.parseInt(numberStr.replace("注",""));
            List<String> list = new ArrayList<>();
            switch (type) {
                case "福彩3D":
                    list=TicketUtils.threeD(number);
                    break;
                case "双色球":
                    list=TicketUtils.twoColorBall(number);
                    break;
                case "七乐彩":
                    list=TicketUtils.sevenTicket(number);
                    break;
                case "大乐透":
                    list=TicketUtils.lotteryTicket(number);
                    break;
                case "七星彩":
                    list=TicketUtils.sevenStartTicket(number);
                    break;
                case "排列3":
                    list=TicketUtils.arrangeThree(number);
                    break;
                case "排列5":
                    list=TicketUtils.arrangeFive(number);
                    break;
            }

            StringBuffer result = new StringBuffer();
            for (int i = 0; i < number; i++) {
                result.append(type);
                result.append("第 ");
                result.append(i+1);
                result.append(" 注：" );
                result.append(  list.get(i));
                result.append("\n");
                }
            outArea.setText(result.toString());

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


}


