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

    public final static int NUMBER_Point = -1;
    public final static int NUMBER_0 = 0;
    public final static int NUMBER_1 = 1;
    public final static int NUMBER_2 = 2;
    public final static int NUMBER_3 = 3;
    public final static int NUMBER_4 = 4;
    public final static int NUMBER_5 = 5;
    public final static int NUMBER_6 = 6;
    public final static int NUMBER_7 = 7;
    public final static int NUMBER_8 = 8;
    public final static int NUMBER_9 = 9;
    public final static int NUMBER_a = 10;
    public final static int NUMBER_b = 11;
    public final static int NUMBER_c = 12;
    public final static int NUMBER_d = 13;
    public final static int NUMBER_e = 14;
    public final static int NUMBER_f = 15;

    public final static int SYMBLE_REMAINDER = 0;  // 求余
    public final static int SYMBLE_POW = 1;        // 乘方
    public final static int SYMBLE_SQRT = 2;       // 开根
    public final static int SYMBLE_ADD = 3;        // 加
    public final static int SYMBLE_SUBSTRUCT = 4;  //减
    public final static int SYMBLE_MULTIPLY = 5;   //乘
    public final static int SYMBLE_DIVIDE = 6;     //除

    public final static int OPERATION_CALCULATE = 0;     // 归零
    public final static int OPERATION_AC = 0;// 等于号

    @FXML
    public Button number;
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

    @FXML
    public TextField show;
    public ListView listView;
    public GridPane girdPane;
    private ArrayList<String> list = new ArrayList<>();
    private ObservableList<String> listViewData = FXCollections.observableArrayList();

    public Control[] numberControls;
    public Control[] symbleControls;
    public Control[] operationControls;

    // 定义状态
    public String[] symbles = new String[] { "求余", "乘方", "开根", "+", "-", "*", "/" };
    public String[] numbers = new String[] { "", "", ""};
    public int nowNumber = 1; // 1: 第一个数字 2：第二个数字
    public boolean hasPoint = false; // 是否有小数点
    public boolean hasSymble = false; // 是否有加减乘除的符号
    public boolean hasNumber = false; // 是否有数字
    public int symble = 0;
    public String result = "";

    @FXML
    public void initialize() {
        super.initialize();

        // 循环添加事件
        numberControls = new Control[] {
                number, number0, number1,
                number2, number3, number4,
                number5, number6, number7,
                number8, number9 };

        symbleControls = new Control[] {
                symble0, symble1, symble2,
                symble3, symble4, symble5,
                symble6 };

        operationControls = new Control[] { operation0, operation1 };

        // 数字键绑定监听事件
        for (int i = NUMBER_Point; i <= NUMBER_9; i++) {
            final int finalI = i;
            numberControls[i].setOnMouseClicked(me -> {
                pressNumber(finalI);
            });
        }
        // 运算符
        for (int i = SYMBLE_REMAINDER; i <= SYMBLE_DIVIDE; i++) {
            final int finalI = i;
            symbleControls[i].setOnMouseClicked(me -> {
                pressSymble(finalI);
            });
        }
        // 指令
        operation0.setOnMouseClicked(me -> {
            pressOperation(OPERATION_CALCULATE);
        });
        operation1.setOnMouseClicked(me -> {
            pressOperation(OPERATION_AC);
        });

        // 更新标签大小
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
            if (i != NUMBER_Point) {
                numbers[nowNumber] += String.valueOf(i);
            }
        }
        if (!hasPoint && hasNumber) {
            if (i != NUMBER_Point) {
                numbers[nowNumber] += String.valueOf(i);
            } else {
                hasPoint = true;
                numbers[nowNumber] += ".";
            }
        }
        if (!hasPoint && !hasNumber) {
            if (i != NUMBER_Point) {
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
        hasPoint = false;
        showText();
    }

    // 按下等于和归零
    public void pressOperation(int i) {
        try { // 是否符合规范在这里捕获
            if (i == OPERATION_AC) {
                init();
            }
            if (i == OPERATION_CALCULATE) {
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

    private BigDecimal calculator(BigDecimal bd1, BigDecimal bd2, int i) throws InputException {
        return calculator(bd1, bd2, i, 10);
    }

    private BigDecimal calculator(BigDecimal bd1, BigDecimal bd2, int i, int precision) throws InputException {
        BigDecimal res = null;
        MathContext mc = new MathContext(precision);
        try {
            switch (i) {
            case SYMBLE_REMAINDER:
                res = bd1.divideAndRemainder(bd2,mc)[1];
                break;
            case SYMBLE_POW:
                res = bd1.pow(bd2.intValue(), mc);
                numbers[2] = String.valueOf(bd2.intValue());
                break;
            case SYMBLE_SQRT:
                res = new BigDecimal(Math.pow(bd1.doubleValue(), 1 / bd2.doubleValue()), mc);
                break;
            case SYMBLE_ADD:
                res = bd1.add(bd2, mc);
                break;
            case SYMBLE_SUBSTRUCT:
                res = bd1.subtract(bd2, mc);
                break;
            case SYMBLE_MULTIPLY:
                res = bd1.multiply(bd2, mc);
                break;
            case SYMBLE_DIVIDE:
                res = bd1.divide(bd2, precision, RoundingMode.HALF_UP);
                break;
            default:
                res = bd1;
            }
        } catch (Exception e) {
            // 抛出自定义的异常
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
