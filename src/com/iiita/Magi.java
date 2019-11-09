package com.iiita;

import com.iiita.api.QuestionEngine;
import com.iiita.controllers.AnswerController;
import com.iiita.controllers.MagiController;
import com.iiita.models.HarryPotterCharacter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Magi extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    private Scene questionScene, answerScene;
    private AnswerController answerController;
    private EventHandler<MouseEvent> handler1, handler2;
    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    public void showQuestionScene() {
        stage.setScene(questionScene);
        stage.show();
    }
    public void showAnswerScene(HarryPotterCharacter hpc) {
        stage.setScene(answerScene);
        answerController.setAnswer(hpc);
        stage.show();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        handler1 = event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        };
        handler2 = event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        };
        primaryStage.setTitle("Magi");
        primaryStage.initStyle(StageStyle.UNDECORATED);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("res/magi_question_ui.fxml"));
        Parent root = fxmlLoader.load();
        root.setOnMousePressed(handler1);
        root.setOnMouseDragged(handler2);
        MagiController magiController = fxmlLoader.getController();
        magiController.setApp(this);
        magiController.setQuestionEngine(new QuestionEngine());

        questionScene = new Scene(root, 1024, 768);
        primaryStage.setScene(questionScene);
        primaryStage.show();
        magiController.start();

        fxmlLoader = new FXMLLoader(getClass().getResource("res/magi_dialog.fxml"));
        root = fxmlLoader.load();
        root.setOnMousePressed(handler1);
        root.setOnMouseDragged(handler2);
        answerController = fxmlLoader.getController();
        answerController.onClose(stage);
        answerScene = new Scene(root, 1024, 768);
    }
    public void close(){
        stage.close();
    }



}
