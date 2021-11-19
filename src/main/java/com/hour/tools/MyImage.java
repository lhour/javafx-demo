package com.hour.tools;

import com.hour.exception.ImageCopyException;

import java.io.*;

public class MyImage {

    public static void copyImage(String path, String target) throws ImageCopyException {
        File begin = new File(path);
        File end = new File(target);
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(begin);
            out = new FileOutputStream(end);
            byte[] bs = in.readAllBytes();
            out.write(bs);
        } catch (Exception e) {
            throw new ImageCopyException("图片保存或添加失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
