package com.xtool.manager.components;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.xtool.manager.components.base.Overdrive;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertDemo extends Overdrive {

    private Button leftButton;

    @Override
    public Node build() {
        leftButton = new JFXButton("Alert");
        leftButton.setLayoutX(50);
        leftButton.setLayoutY(50);
        return new Group(leftButton);
    }

    @Override
    protected void afterShow(Stage stage) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."));
        JFXAlert<Void> alert = new JFXAlert<>(stage);
        alert.setOverlayClose(true);
        alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
        alert.setContent(layout);
        alert.initModality(Modality.NONE);
        leftButton.setOnAction(action-> alert.showAndWait());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
