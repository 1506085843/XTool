package com.xtool.manager.gui.uicomponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.xtool.manager.utils.DayOfWeekEnum;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

import static java.time.temporal.ChronoUnit.DAYS;

@ViewController(value = "/fxml/ui/DateCalculation.fxml", title = "日期计算")
public class DateCalculationController {
    @FXML
    private JFXTextField year1;
    @FXML
    private JFXComboBox<Label> month1;
    @FXML
    private JFXTextField day1;
    @FXML
    private JFXComboBox<Label> beforeOrafter;
    @FXML
    private JFXTextField daysNumber;
    @FXML
    private JFXButton dateCalculatButton;
    @FXML
    private Label dateCalculatResult;


    @FXML
    private JFXTextField year2;
    @FXML
    private JFXTextField year3;
    @FXML
    private JFXComboBox<Label> month2;
    @FXML
    private JFXComboBox<Label> month3;
    @FXML
    private JFXTextField day2;
    @FXML
    private JFXTextField day3;
    @FXML
    private JFXButton dateMinusButton;
    @FXML
    private Label dateMinusResult;

    @FXML
    private JFXTextField year4;
    @FXML
    private JFXComboBox<Label> month4;
    @FXML
    private JFXTextField day4;
    @FXML
    private JFXButton calendarButton;
    @FXML
    private JFXDatePicker jFXDatePicker;
    @FXML
    private Label calendarResult;


    private static LocalDate nowData = LocalDate.now();


    @PostConstruct
    public void init() {
        //初始化年月日、天数相关数据
        initYearMonthDay(year1, month1, day1, true);
        daysNumber.setText("100");
        initYearMonthDay(year2, month2, day2, false);
        initYearMonthDay(year3, month3, day3, false);
        year3.setText(String.valueOf(nowData.getYear() + 1));
        initYearMonthDay(year4, month4, day4, false);
        jFXDatePicker.setValue(nowData);


        //推算日期按钮事件
        dateCalculatButton.setOnAction(action -> {
            dateCalculatResult.setText("");
            String year = year1.getText();
            String month = getValueFromJFXComboBox(month1);
            String day = day1.getText();
            String culatDay = daysNumber.getText();
            int beforeAfter = beforeOrafter.getSelectionModel().getSelectedIndex();
            if (isErrorNumber(year, culatDay)) {
                dateCalculatResult.setText("你的输入有误，请重新输入");
                return;
            }
            if (isErrorDay(year, month, day)) {
                dateCalculatResult.setText("你的日期输入有误，请重新输入");
                return;
            }
            LocalDate today = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            LocalDate resultLocalDate = beforeAfter == 1 ? today.plus(Integer.parseInt(culatDay), DAYS) : today.minus(Integer.parseInt(culatDay), DAYS);
            DayOfWeek dayOfWeek = resultLocalDate.getDayOfWeek();
            dateCalculatResult.setText(resultLocalDate + " " + DayOfWeekEnum.getde(dayOfWeek.getValue()));
        });

        //计算日期差按钮事件
        dateMinusButton.setOnAction(action -> {
            dateMinusResult.setText("");
            String year2Value = year2.getText();
            String year3Value = year3.getText();
            String month2Value = getValueFromJFXComboBox(month2);
            String month3Value = getValueFromJFXComboBox(month3);
            String day2Value = day2.getText();
            String day3Value = day3.getText();
            if (isErrorNumber(year2Value, year3Value)) {
                dateMinusResult.setText("你的年份输入有误，请重新输入");
                return;
            }
            if (isErrorDay(year2Value, month2Value, day2Value) || isErrorDay(year3Value, month3Value, day3Value)) {
                dateMinusResult.setText("你的日期输入有误，请重新输入");
                return;
            }
            LocalDate start = LocalDate.of(Integer.parseInt(year2Value), Integer.parseInt(month2Value), Integer.parseInt(day2Value));
            LocalDate end = LocalDate.of(Integer.parseInt(year3Value), Integer.parseInt(month3Value), Integer.parseInt(day3Value));
            long intervalDay = DAYS.between(start, end);

            String GapDateMinusStr = getGapDateMinus(start, end);

            GapDateMinusStr = GapDateMinusStr.length() > 0 ? "(" + GapDateMinusStr + ")" : GapDateMinusStr;

            dateMinusResult.setText("相差： " + intervalDay + "天 "+GapDateMinusStr);
        });

        //获取日历按钮事件
        calendarButton.setOnAction(action -> {
            calendarResult.setText("");
            String year4Value = year4.getText();
            String month4Value = getValueFromJFXComboBox(month4);
            String day4Value = day4.getText();
            if (isErrorNumber(year4Value)) {
                calendarResult.setText("你的年份输入有误，请重新输入");
                return;
            }
            if (isErrorDay(year4Value, month4Value, day4Value)) {
                calendarResult.setText("你的日期输入有误，请重新输入");
                return;
            }
            jFXDatePicker.setValue(LocalDate.of(Integer.parseInt(year4Value), Integer.parseInt(month4Value), Integer.parseInt(day4Value)));
            //显示日历
            jFXDatePicker.show();
        });


    }

    //初始化年输入框和月、日下拉框
    public void initYearMonthDay(TextField year, JFXComboBox<Label> month, JFXTextField day, boolean hasBeforeOrafter) {
        //获取当前年月日
        int currenytYear = nowData.getYear();
        int currenytMonth = nowData.getMonth().getValue();
        int currenytMonthDay = nowData.getDayOfMonth();

        //初始化年输入框
        year.setText(String.valueOf(currenytYear));
        //初始化月下拉框
        SingleSelectionModel<Label> month1Model = month.getSelectionModel();
        month1Model.select(currenytMonth - 1);
        month.setSelectionModel(month1Model);
        //初始化日下拉框的数据并设置默认选中的数据
        if (null != day) {
            day.setText(String.valueOf(currenytMonthDay));
        }
        if (hasBeforeOrafter) {
            //初始化向前向后下拉框的数据
            SingleSelectionModel<Label> beforeOrafterModel = beforeOrafter.getSelectionModel();
            beforeOrafterModel.select(1);
            beforeOrafter.setSelectionModel(beforeOrafterModel);
        }
    }


    //检查该输入的数字是否是错误的数字（即是否含有特殊字符）
    public boolean isErrorNumber(String... numbers) {
        for (String number : numbers) {
            if (number.startsWith("0") || number.contains(".")) {
                return true;
            }
            char[] ch = number.toCharArray();
            for (char c : ch) {
                int i = (int) c;
                if (i < 48 || i > 57) {
                    return true;
                }
            }
            try {
                Integer.valueOf(number);
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }

    //判断输入的day的范围是否是错误的
    public boolean isErrorDay(String year, String month, String day) {
        LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        int monthDay = localDate.lengthOfMonth();
        try {
            int dayNumber = Integer.parseInt(day);
            if (dayNumber > 0 && dayNumber <= monthDay) {
                return false;
            }
        } catch (NumberFormatException e) {
            return true;
        }
        return true;
    }

    //从JFXComboBox中获取下拉框的值
    public String getValueFromJFXComboBox(JFXComboBox<Label> jFXComboBox) {
        Label label = jFXComboBox.getValue();
        return label.getText();
    }

    //计算两个日期差，返回相差 X年X月X日
    public String getGapDateMinus(LocalDate start, LocalDate end) {
        Period period = Period.between(start, end);
        int gapYear = period.getYears();
        int gapMonth = period.getMonths();
        int gapDay = period.getDays();
        String result = "";

        if (gapYear != 0) {
            return "即 " + gapYear + " 年 " + gapMonth + " 月 " + gapDay + " 日";
        }
        if (gapMonth != 0) {
            return "即 " + gapMonth + " 月 " + gapDay + " 日";
        }
        return result;
    }

}



