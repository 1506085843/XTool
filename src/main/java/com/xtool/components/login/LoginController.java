package com.xtool.components.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.xtool.model.FxmlEnum;
import com.xtool.model.User;
import com.xtool.stagemanage.ControlledStage;
import com.xtool.stagemanage.StageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginController implements ControlledStage {

    StageController myController;
    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField password;

    @FXML
    protected void loginAction(ActionEvent actionEvent) {
        User user = new User(userName.getText(),password.getText());

        if (LoginServer.loginCheck(user)) {
            myController.setStage(FxmlEnum.content.getName(),FxmlEnum.login.getName());
        }
    }


    @Override
    public void setStageController(StageController stageController) {
        this.myController = stageController;
    }
}