package com.hour;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 300, 220);
        stage.setScene(scene);
        stage.show();
    }

    // 更改 scene
    public static void setRoot(String fxml) {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 更改 scene
    public static void setRoot(String fxml, int width, int height) {
        setRoot(fxml);
        scene.getWindow().setHeight(height);
        scene.getWindow().setWidth(width);
    }

    // 加载 FXML 文件
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    // 设置 css 文件
    public static void setStyle(String name) {
        scene.getStylesheets().addAll(App.class.getResource("css/" + name + ".css").toExternalForm());
    }

    public static void main(String[] args) throws IOException {
        launch();
    }

}