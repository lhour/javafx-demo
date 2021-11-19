package com.hour.controller;

import com.hour.exception.InputException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

public class CalculatorController extends HomeController {

    @FXML
    public TextField show;

    @FXML
    public ListView listView;
    @FXML
    public GridPane girdPane;

    private ArrayList<String> list = new ArrayList<>();
    private ObservableList<String> listViewData = FXCollections.observableArrayList();

    @FXML
    public Button number; // .
    public Button number0;
    public Button number1;
    public Button number2;
    public Button number3;
    public Button number4;
    public Button number5;
    public Button number6;
    public Button number7;
    public Button number8;
    public Button number9;

    // 求余
    public Button symble0;
    // 乘方
    public Button symble1;
    // 开根
    public Button symble2;
    // 加
    public Button symble3;
    // 减
    public Button symble4;
    // 乘
    public Button symble5;
    // 除
    public Button symble6;

    // 等于
    public Button operation0;
    // 归零
    public Button operation1;

    public Control[] numberControls;
    public Control[] symbleControls;
    public Control[] operationControls;

    public String[] symbles = new String[] { "求余", "乘方", "开根", "+", "-", "*", "/" };
    public String[] numbers = new String[] { "", "", "" };
    public int nowNumber = 1; // 0: 第一个数字 1：第二个数字
    public boolean hasPoint = false; // 是否有小数点
    public boolean hasSymble = false; // 是否有加减乘除的符号
    public boolean hasNumber = false; // 是否有数字
    public int symble = 0;
    String result = "";

    @FXML
    public void initialize() {
        super.initialize();

        // 循环添加事件
        numberControls = new Control[] { number, number0, number1, number2, number3, number4, number5, number6, number7,
                number8, number9 };
        symbleControls = new Control[] { symble0, symble1, symble2, symble3, symble4, symble5, symble6 };
        operationControls = new Control[] { operation0, operation1 };

        for (int i = 0; i < 11; i++) {
            final int finalI = i;
            numberControls[i].setOnMouseClicked(me -> {
                pressNumber(finalI - 1);
            });
        }
        for (int i = 0; i <= 6; i++) {
            final int finalI = i;
            symbleControls[i].setOnMouseClicked(me -> {
                pressSymble(finalI);
            });
        }
        operation0.setOnMouseClicked(me -> {
            pressOperation(0);
        });
        operation1.setOnMouseClicked(me -> {
            pressOperation(1);
        });

        updateLabelSize();

        girdPane.widthProperty().addListener(me -> {
            updateLabelSize();
        });

    }

    private void updateLabelSize() {
        for (Control c : numberControls) {
            c.setPrefHeight(girdPane.getHeight() * 0.5);
            c.setPrefWidth(girdPane.getWidth() * 0.5);
        }
        for (Control c : symbleControls) {
            c.setPrefHeight(girdPane.getHeight() * 0.5);
            c.setPrefWidth(girdPane.getWidth() * 0.5);
        }
        for (Control c : operationControls) {
            c.setPrefHeight(girdPane.getHeight() * 0.5);
            c.setPrefWidth(girdPane.getWidth() * 0.5);
        }
    }

    // 按下数字键
    public void pressNumber(int i) {
        if (hasPoint && hasNumber) {
            if (i != -1) {
                numbers[nowNumber] += String.valueOf(i);
            }
        }
        if (!hasPoint && hasNumber) {
            if (i != -1) {
                numbers[nowNumber] += String.valueOf(i);
            }
            if (i == -1) {
                hasPoint = true;
                numbers[nowNumber] += ".";
            }
        }
        if (!hasPoint && !hasNumber) {
            if (i == -1) {
                hasPoint = true;
                numbers[nowNumber] += ".";
            } else {
                numbers[nowNumber] += String.valueOf(i);
            }
            hasNumber = true;
        }
        showText();
    }

    // 按下加减乘除
    public void pressSymble(int i) {
        if ("".equals(numbers[1])) {
            numbers[1] = "0";
        }
        symble = i;
        hasSymble = true;
        nowNumber = 2;
        showText();
    }

    // 按下等于和归零
    public void pressOperation(int i) {
        try { // 是否符合规范在这里捕获
            if (i == 1) {
                init();
            }
            if (i == 0) {
                calculator(); // 结果赋予result
                String temp = result;
                updateCache(); // 更新缓存
                init(); // 初始化结果
                // 将上一次的结果赋给下一轮
                numbers[1] = temp;
                hasNumber = true;
                if (temp.contains(".")) {
                    hasPoint = true;
                }
            }
            showText();
        } catch (InputException e) {
            init();
            show.setText("error"); // 屏幕显示 error
        }
    }

    private void calculator() throws InputException {
        if (nowNumber == 2) {
            BigDecimal bd1 = new BigDecimal(numbers[1]);
            BigDecimal bd2 = new BigDecimal(numbers[2]);
            BigDecimal res = calculator(bd1, bd2, symble);
            result = res.doubleValue() + "";
        }
    }

    // 抛出自定义的异常
    private BigDecimal calculator(BigDecimal bd1, BigDecimal bd2, int i) throws InputException {
        BigDecimal res = null;
        try {
            switch (i) {
            case 0:
                res = bd1.divideAndRemainder(bd2, new MathContext(10))[1];
                break;
            case 1:
                res = bd1.pow(bd2.intValue(), new MathContext(10));
                numbers[2] = String.valueOf(bd2.intValue());
                break;
            case 2:
                res = new BigDecimal(Math.pow(bd1.doubleValue(), 1 / bd2.doubleValue()), new MathContext(10));
                break;
            case 3:
                res = bd1.add(bd2, new MathContext(10));
                break;
            case 4:
                res = bd1.subtract(bd2, new MathContext(10));
                break;
            case 5:
                res = bd1.multiply(bd2, new MathContext(10));
                break;
            case 6:
                res = bd1.divide(bd2, 10, RoundingMode.HALF_UP);
                break;
            default:
                res = bd1;
            }
        } catch (Exception e) {
            throw new InputException("输入错误");
        }
        return res;
    }

    // 计算过程显示在屏幕上
    private void showText() {
        StringBuilder sb = new StringBuilder();
        String s = numbers[1];
        formatNumberText(sb, s);

        if (hasSymble) {
            sb.append(symbles[symble]);
        }
        if (nowNumber == 2) {
            s = numbers[2];
            formatNumberText(sb, s);
        }
        show.setText(sb.toString());
    }

    // 显示格式化
    private void formatNumberText(StringBuilder sb, String s) {
        if (s.length() > 0) {
            // .前加0
            if (s.charAt(0) == '.') {
                s = "0" + s;
            }
            // .后无数字去点
            if (s.charAt(s.length() - 1) == '.') {
                s.replace(".", "");
            }
        } else {
            s = "";
        }
        sb.append(s);
    }

    // 清空
    public void init() {
        numbers = new String[] { "", "", "" };
        nowNumber = 1; // 0: 第一个数字 1：第二个数字
        hasPoint = false; // 是否有小数点
        hasSymble = false; // 是否有加减乘除的符号
        hasNumber = false; // 是否有数字
        symble = 0;
        result = "";
    }

    // 更新右侧缓存
    public void updateCache() {
        list.add(show.getText() + "=" + result);
        listViewData.clear();
        for (String cache : list) {
            listViewData.add(cache);
        }
        listView.setItems(listViewData);
    }
}
