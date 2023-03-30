package com;

import com.xtool.model.FxmlEnum;
import com.xtool.stagemanage.StageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class App extends Application {

    private StageController stageController = new StageController();

    @Override
    public void start(Stage stage) throws IOException {
        //遍历FxmlEnum中的所有舞台,然后加入到stageController中
        for (FxmlEnum value : FxmlEnum.values()) {
            stageController.loadStage(value.getName(),value.getFxml());
        }

        //显示login舞台
        stageController.setStage(FxmlEnum.login.getName());
    }



    public static void main(String[] args) {
        launch();
    }
}