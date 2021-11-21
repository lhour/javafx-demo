package com.hour.controller;

import com.hour.App;
import com.hour.tools.MyFile;
import com.hour.tools.MyTime;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class TvController extends HomeController {

    @FXML
    public Slider progressBar;
    @FXML
    public Slider voiceBar;
    @FXML
    public MediaView player;
    @FXML
    public Button choose;
    @FXML
    public javafx.scene.layout.HBox HBox;
    @FXML
    public Label progressLabel;
    @FXML
    public Label voiceLabel;
    // 是否播放
    private static AtomicBoolean play = new AtomicBoolean(false);

    // 监听器模式
    public static TextField path = new TextField();

    @FXML
    public void initialize() {
        super.initialize();

        // 控制播放视频资源路径
        path.textProperty().addListener(me -> {
            playVideo(path.getText());
        });

        voiceBar.setValue(80);

        // 控制单击停止
        player.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 1) {
                if (play.getAcquire()) {
                    pauseVideo();
                } else {
                    playVideo();
                }
            }
        });
        // 按下暂停
        progressBar.setOnMousePressed(me -> {
            pauseVideo();
        });
        // 松开开始并调整
        progressBar.setOnMouseReleased(me -> {
            player.getMediaPlayer()
                    .seek(player.getMediaPlayer().getMedia().getDuration().multiply(progressBar.getValue() / 100));
            playVideo();
        });
        // 声音
        voiceBar.valueProperty().addListener(me -> {
            player.getMediaPlayer().setVolume(voiceBar.getValue() * 100);
            voiceLabel.setText((int) voiceBar.getValue() + "%");
        });

        borderPane.widthProperty().addListener(me -> {
            double width = borderPane.getWidth();
            player.setFitWidth(width * 0.8);
            player.setFitHeight(width * 0.8 * 0.56);
            HBox.setLayoutX(HBox.getLayoutX() + width * 0.4);
        });

        try {
            borderPane.setTop(new Scene(App.loadFXML("menu")).getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void changePath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开视频文件");
        MyFile.toMVFile(fileChooser);
        try{
            String now = fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
            path.setText(now);
        } catch (Exception e){
            System.out.println("关闭了文件选择框");
        }
    }

    // 指定资源播放
    public void playVideo(String path) {
        Media media = new Media(
                "file:///" + new File(path).getAbsolutePath().replaceFirst("\\\\", "//").replaceAll("\\\\", "/"));
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        String minute = media.getDuration().toSeconds() / 60 + "";
        String seconds = media.getDuration().toSeconds() % 60 + "";

        setProgressLabel(mediaPlayer);
        // 进度条更新
        mediaPlayer.currentTimeProperty().addListener(me -> {
            updateTime(mediaPlayer);
        });

        player.setMediaPlayer(mediaPlayer);
        progressBar.setValue(0);
        playVideo();
    }

    // 播放视频
    public void playVideo() {
        play.compareAndSet(false, true);
        player.getMediaPlayer().play();
    }

    // 暂停视频
    public void pauseVideo() {
        play.compareAndSet(true, false);
        player.getMediaPlayer().pause();
    }

    // 更新进度条
    public void updateTime(MediaPlayer mediaPlayer) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (play.getAcquire()) {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    // 设置进度条
                    progressBar
                            .setValue(currentTime.toSeconds() / mediaPlayer.getMedia().getDuration().toSeconds() * 100);
                    setProgressLabel(mediaPlayer);
                }
            }
        });
    }

    // 进度条右边数字
    public void setProgressLabel(MediaPlayer mediaPlayer) {
        progressLabel.setText(MyTime.formatMVTime(mediaPlayer.getCurrentTime().toSeconds(),
                mediaPlayer.getStopTime().toSeconds(), 2));
    }
}
