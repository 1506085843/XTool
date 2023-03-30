package com.xtool.model;

public enum FxmlEnum {

    login("login", "/fxml/login.fxml"),
    content("content", "/fxml/content.fxml");

    FxmlEnum(String name, String fxml) {
        this.name = name;
        this.fxml = fxml;
    }

    private String name;
    private String fxml;

    public String getName() {
        return name;
    }

    public String getFxml() {
        return fxml;
    }

}

