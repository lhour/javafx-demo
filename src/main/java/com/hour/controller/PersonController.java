package com.hour.controller;

import com.hour.model.Person;
import com.hour.tools.MyPerson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonController extends HomeController {
    public Label name1;
    public Label name2;
    public TextField name3;
    public Label sex1;
    public Label sex2;
    public TextField sex3;
    public Label age1;
    public Label age2;
    public TextField age3;
    public Label phone1;
    public Label phone2;
    public TextField phone3;
    public Label email1;
    public Label email2;
    public TextField email3;
    public Label other1;
    public Label other2;
    public TextField other3;

    public ListView list;
    private ObservableList<Button> listViewData = FXCollections.observableArrayList();

    public static List<Person> persons = new ArrayList<Person>();

    public static URL url = HomeController.class.getResource("../persons/person");
    public static File personFile;
    public static File personFile2 = new File("./resources/persons/person");
    HashMap<ListCell<String>, Person> map = new HashMap<>();
    public static Person nowPerson;

    Control[] controls1;
    Control[] controls2;
    Control[] controls3;

    @FXML
    public void initialize() {
        super.initialize();
        // 如果直接在 ide 中打开， 看到的是 target 中 resources 下的文件
        // 如果用 jar 包打开， 看到的是外置文件夹 resources 下的 images
        if (url == null) {
            personFile = personFile2;
        } else {
            personFile = new File(url.getPath());
        }

        // 不在这里初始化会报 空指针异常
        controls1 = new Control[] { name1, sex1, age1, phone1, email1, other1 };
        controls2 = new Control[] { name2, sex2, age2, phone2, email2, other2 };
        controls3 = new Control[] { name3, sex3, age3, phone3, email3, other3 };

        // 开始先隐藏界面
        hideAll();
        if (personFile.length() != 0) {
            persons = new ArrayList<Person>();
            try {
                persons = MyPerson.readPerson(personFile);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (persons.size() > 0) {
            nowPerson = persons.get(0);
            fillContent(nowPerson);
            showLabel();
        }
        // 填充右侧按钮
        fillListView();
    }

    @FXML
    public void addPerson(ActionEvent actionEvent) {
        cleanContent();
        nowPerson = null;
        showText();
    }

    @FXML
    public void changePerson(ActionEvent actionEvent) {
        persons.remove(nowPerson);
        nowPerson = null;
        showText();
    }

    @FXML
    public void deletePerson(ActionEvent actionEvent) {
        // 直接存储上删除, 不需要再点击保存
        persons.remove(nowPerson);
        nowPerson = null;
        cleanContent(); // 删除掉标签上的内容，防止保存空内容
        fillListView();
        hideAll();
    }

    @FXML
    public void savePerson(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        // name 为空不增加
        if ("".equals(name3.getText())) {
            return;
        }
        // 存储上增加
        Person p = new Person();
        p.setName(name3.getText());
        p.setAge(age3.getText());
        p.setSex(sex3.getText());
        p.setPhone(phone3.getText());
        p.setEmail(email3.getText());
        p.setOther(other3.getText());
        persons.add(p);
        // 显示
        nowPerson = p;
        fillContent(p);
        showLabel();
        fillListView();
        MyPerson.WritePerson(personFile, persons);
    }

    public void hideAll() {
        hideControl(controls1);
        hideControl(controls2);
        hideControl(controls3);
    }

    // 显示组件
    public void showLabel() {
        hideControl(controls3);
        showControl(controls1);
        showControl(controls2);
    }

    // 显示组件
    public void showText() {
        hideControl(controls2);
        showControl(controls1);
        showControl(controls3);
    }

    // 隐藏一系列组件
    public void hideControl(Control[] controls) {
        for (Control c : controls) {
            c.setVisible(false);
        }
    }

    public void showControl(Control[] controls) {
        for (Control c : controls) {
            c.setVisible(true);
        }
    }

    public void fillListView() {
        listViewData.clear();
        // 填充 listview
        for (Person p : persons) {
            Button b = new Button();
            b.setId("button-person");
            listViewData.add(b);
            b.setText(p.getName());
            b.setOnMouseClicked(me -> {
                if (me.getClickCount() == 1) {
                    fillContent(p);
                    nowPerson = p;
                    showLabel();
                }
            });
        }
        list.setItems(listViewData);
    }

    // 把联系人信息放上去
    public void fillContent(int index) {
        if (index < 0 || index >= persons.size()) {
            hideAll();
        } else {
            Person p = persons.get(index);
            fillContent(p);
        }
    }

    // 填充界面信息
    public void fillContent(Person p) {
        name2.setText(p.getName());
        sex2.setText(p.getSex());
        age2.setText(p.getAge());
        phone2.setText(p.getPhone());
        email2.setText(p.getEmail());
        other2.setText(p.getOther());
        name3.setText(p.getName());
        sex3.setText(p.getSex());
        age3.setText(p.getAge());
        phone3.setText(p.getPhone());
        email3.setText(p.getEmail());
        other3.setText(p.getOther());
    }

    // 清除界面信息
    public void cleanContent() {
        name2.setText("");
        sex2.setText("");
        age2.setText("");
        phone2.setText("");
        email2.setText("");
        other2.setText("");
        name3.setText("");
        sex3.setText("");
        age3.setText("");
        phone3.setText("");
        email3.setText("");
        other3.setText("");
    }

}
