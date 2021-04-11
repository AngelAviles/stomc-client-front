package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static ClientFrontController clientFrontController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientFront.fxml"));
        Parent root = loader.load();

        clientFrontController = loader.getController();
        primaryStage.setTitle("STOMC Client - Front");
        primaryStage.setScene(new Scene(root, 1060, 700));
        primaryStage.setOnCloseRequest(windowEvent -> System.exit(0));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}