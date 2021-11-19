package com.hour.tools;

import javafx.stage.FileChooser;

public class MyFile {

    // 限定只能选择 视频文件
    public static void toMVFile(final FileChooser fc) {
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp4", "*.MP4", "*.MP4"),
                new FileChooser.ExtensionFilter("mov", "*.mov", "*.MOV"),
                new FileChooser.ExtensionFilter("avi", "*.avi", "*.AVI"));
    }

    // 限定只能选择 图片文件
    public static void toImageFile(final FileChooser fc) {
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("jpg", "*.jpg", "*.jpg"),
                new FileChooser.ExtensionFilter("png", "*.png", "*.PNG"));
    }

}
