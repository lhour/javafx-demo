package com.hour.controller;

import com.hour.App;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomeController {

    @FXML
    public BorderPane borderPane;

    @FXML
    public void initialize() {
        setMenu();
    }

    public void setMenu() {
        try {
            borderPane.setTop(new Scene(App.loadFXML("menu")).getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
