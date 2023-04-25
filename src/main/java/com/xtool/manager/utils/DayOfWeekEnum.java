package com.xtool.manager.utils;

public enum DayOfWeekEnum {
    MONDAY(1, "星期一"),
    TUESDAY(2, "星期二"),
    WEDNESDAY(3, "星期三"),
    THURSDAY(4, "星期四"),
    FRIDAY(5, "星期五"),
    SATURDAY(6, "星期六"),
    SUNDAY(7, "星期日");


    DayOfWeekEnum(int number, String description) {
        this.code = number;
        this.description = description;
    }

    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    //根据code查找description
    public static String getde(int v) {
        for (DayOfWeekEnum s : DayOfWeekEnum.values()) {
            if (v == s.getCode()) {
                return s.getDescription();
            }
        }
        return "";
    }

}
