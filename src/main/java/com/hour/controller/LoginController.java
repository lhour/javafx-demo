package com.hour.controller;

import com.hour.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label msg;

    @FXML
    public void login(ActionEvent actionEvent) throws IOException {
        if ("user".equals(username.getText()) && "admin".equals(password.getText())) {
            App.setRoot("home", 600, 400);
        } else {
            msg.setText("账号或密码错误！请重新输入！");
        }
    }
}
