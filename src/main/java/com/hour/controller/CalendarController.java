package com.hour.controller;

import com.hour.exception.NotThisMonthException;
import com.hour.tools.MyDate;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.Calendar;

public class CalendarController extends HomeController {
    @FXML
    public Button update1;
    @FXML
    public Button monthDown;
    @FXML
    public TextField monthLabel;
    @FXML
    public Button monthUp;
    @FXML
    public Label today;
    @FXML
    public Button yearDown;
    @FXML
    public TextField yearLabel;
    @FXML
    public Button yearUp;
    @FXML
    public Button update2;
    @FXML
    public Button toToday;
    @FXML
    public GridPane gridPane;

    public static Calendar calendar = Calendar.getInstance();

    public static int nowDay = calendar.get(Calendar.DAY_OF_MONTH);

    public static int nowMonth = calendar.get(Calendar.MONTH) + 1;

    public static int nowYear = calendar.get(Calendar.YEAR);

    public static String[] weekTool = new String[] { "日", "一", "二", "三", "四", "五", "六" };

    @Override
    public void initialize() {
        super.initialize();
        fill();

        // 点击月份减一
        monthDown.setOnMousePressed(me -> {
            clear();
            if (me.getClickCount() == 1) {
                --nowMonth;
                if (nowMonth == 0) {
                    --nowYear;
                    nowMonth = 12;
                }
            }
            fill();
        });
        // 点击月份加一
        monthUp.setOnMousePressed(me -> {
            clear();
            if (me.getClickCount() == 1) {
                ++nowMonth;
                if (nowMonth == 13) {
                    ++nowYear;
                    nowMonth = 1;
                }
            }
            fill();
        });
        // 点击年份减一
        yearDown.setOnMousePressed(me -> {
            clear();
            if (me.getClickCount() == 1) {
                --nowYear;
            }
            fill();
        });
        // 点击月份加一
        yearUp.setOnMousePressed(me -> {
            clear();
            if (me.getClickCount() == 1) {
                ++nowYear;
            }
            fill();
        });

        update1.setOnMousePressed(me -> {
            updateLabel();
        });
        update2.setOnMousePressed(me -> {
            updateLabel();
        });
        // 回到今天的日期
        toToday.setOnMouseClicked(me -> {
            calendar.clear(); // 防止在别的地方更改过
            calendar = Calendar.getInstance();
            nowYear = calendar.get(Calendar.YEAR);
            nowMonth = calendar.get((Calendar.MONTH)) + 1;
            nowDay = calendar.get(Calendar.DAY_OF_MONTH);
            clear();
            fill();
        });
    }

    // 第一行内容填充
    public void fillTitle() {
        for (int i = 0; i < 7; i++) {
            Button button = new Button();
            button.setId("title-button");
            button.setText(weekTool[i]);
            gridPane.add(button, i, 0);
        }
    }

    // 填充按钮
    public void fillController() {
        monthLabel.setText(nowMonth + "月");
        yearLabel.setText(nowYear + "年");
        today.setText(nowYear + "年" + nowMonth + "月" + nowDay + "日");
    }

    // 日期数字填充
    public void fillContent() throws NotThisMonthException {
        int weekDay = MyDate.getFirstDayOfWeek(nowYear, nowMonth);
        int days = MyDate.getDaysOfMonth(nowYear, nowMonth);
        for (int i = 0; i < days; i++) {
            Button button = new Button();
            button.setId("date");
            button.setText(String.valueOf(i + 1));
            if (i + 1 == nowDay) {
                button.setId("now-date");
            }
            button.setOnMouseClicked(me -> {
                nowDay = Integer.valueOf(button.getText());
                clear();
                fill();
            });
            gridPane.add(button, (weekDay + i) % 7, (weekDay + i) / 7 + 1);
        }
    }

    public void fill() {
        try {
            fillTitle();
            fillContent();
            fillController();
        } catch (NotThisMonthException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        gridPane.getChildren().clear();
    }

    public void updateLabel() {
        String y = yearLabel.getText().replace("年", "");
        String m = monthLabel.getText().replace("月", "");
        int year = Integer.valueOf(y);
        int month = Integer.valueOf(m);
        nowYear = year;
        if (month <= 0 || month > 12) {
            month = nowMonth;
        } else {
            nowMonth = month;
        }
        clear();
        fill();
    }
}
