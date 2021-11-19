package com.hour.controller;

import com.hour.exception.ImageCopyException;
import com.hour.tools.MyFile;
import com.hour.tools.MyImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class ImageController extends HomeController {

    @FXML
    public Button last;
    @FXML
    public Button save;
    @FXML
    public Button next;
    @FXML
    public ImageView imageView;
    @FXML
    public Label count;

    public static int index = 0;
    public static int now = 0;

    public static URL url = MenuController.class.getResource("../images");
    public static File imagesFile;
    public static File imagesFile2 = new File("./resources/images");

    @FXML
    public void initialize() {
        super.initialize();
        // 如果直接在 ide 中打开， 看到的是 target 中 resources 下的文件
        // 如果用 jar 包打开， 看到的是外置文件夹 resources 下的 images
        if (url == null) {
            imagesFile = imagesFile2;
        } else {
            imagesFile = new File(url.getPath());
        }

        if (imagesFile.isDirectory()) {
            index = imagesFile.listFiles().length;
            count.setText("共有" + index + "张图片, 当前第" + (now + 1) + "张");
            if (index > 0) {
                Image image = new Image(imagesFile.listFiles()[0].toURI().toString());
                imageView.setImage(image);
            }
        }

        borderPane.widthProperty().addListener(me -> {
            double width = borderPane.getWidth();
            imageView.setFitWidth(width * 0.8);
            imageView.setFitHeight(width * 0.8 * 0.6);
        });
    }

    public void toLastImage(ActionEvent actionEvent) {
        if (now > 0) {
            Image image = new Image(String.valueOf(imagesFile.listFiles()[--now].toURI()));
            imageView.setImage(image);
            count.setText("共有" + index + "张图片, 当前第" + (now + 1) + "张");
        } else {
            count.setText("已经是第一张图片了");
        }
    }

    public void saveImage(ActionEvent actionEvent) throws ImageCopyException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存图片");
        MyFile.toImageFile(fileChooser);
        String path = fileChooser.showSaveDialog(new Stage()).getAbsolutePath();
        MyImage.copyImage(imagesFile.listFiles()[now].getPath(), path);
    }

    public void toNextImage(ActionEvent actionEvent) {
        if (now < index - 1) {
            Image image = new Image(String.valueOf(imagesFile.listFiles()[++now].toURI()));
            imageView.setImage(image);
            count.setText("共有" + index + "张图片, 当前第" + (now + 1) + "张");
        } else {
            count.setText("已经是最后一张图片了");
        }
    }
}
