package com.hour.controller;

import com.hour.App;
import com.hour.exception.ImageCopyException;
import com.hour.tools.MyFile;
import com.hour.tools.MyImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class MenuController extends MenuBar {

    @FXML
    public Menu tools;
    @FXML
    public Menu file;
    @FXML
    public MenuItem openCalculator;
    @FXML
    public MenuItem openCalendar;
    @FXML
    public Menu person;
    @FXML
    public Menu imgs;
    @FXML
    public Menu mv;

    public static URL url = MenuController.class.getResource("../images");
    public static File imagesFile;
    public static File imagesFile2 = new File("./resources/images");

    @FXML
    public void initialize() {

        // 如果直接在 ide 中打开， 看到的是 target 中 resources 下的文件
        // 如果用 jar 包打开， 看到的是外置文件夹 resources 下的 images
        if (url == null) {
            imagesFile = imagesFile2;
        } else {
            imagesFile = new File(url.getPath());
        }
    }

    public void toMV(ActionEvent actionEvent) {
        App.setRoot("tv", 700, 500);
    }

    public void chooseMV(ActionEvent actionEvent) {
        App.setRoot("tv", 700, 500);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开视频文件");
        MyFile.toMVFile(fileChooser);
        String now = fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        TvController.path.setText(now);
    }

    public void openImg(ActionEvent actionEvent) {
        App.setRoot("image", 600, 450);
    }

    public void addImg(ActionEvent actionEvent) throws ImageCopyException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("添加图片");
        MyFile.toImageFile(fileChooser);
        String path = fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
        MyImage.copyImage(path, imagesFile.getPath() + "/" + imagesFile.listFiles().length + ".jpg");
        App.setRoot("image", 600, 450);
    }

    public void openPerson(ActionEvent actionEvent) {
        App.setRoot("person", 600, 450);
        App.setStyle("person");
    }

    public void openCalculator(ActionEvent actionEvent) {
        App.setRoot("calculator", 600, 450);
        App.setStyle("calculator");
    }

    public void openCalender(ActionEvent actionEvent) {
        App.setRoot("calendar", 600, 450);
        App.setStyle("calendar");
    }

}
