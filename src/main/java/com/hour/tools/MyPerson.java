package com.hour.tools;

import com.hour.model.Person;

import java.io.*;
import java.util.List;

public class MyPerson {

    public static List<Person> readPerson(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
        List<Person> list = (List<Person>) objectInputStream.readObject();
        objectInputStream.close();
        return list;
    }

    public static void WritePerson(File file, List<Person> list) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
        objectOutputStream.writeObject(list);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

}
