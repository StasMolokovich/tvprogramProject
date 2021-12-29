package com.tvprogram;

import com.tvprogram.model.Chanel;
import com.tvprogram.model.Program;
import com.tvprogram.util.DateTimeUtils;
import com.tvprogram.util.XMLIOUtil;
import com.tvprogram.util.console.ConsoleReader;
import com.tvprogram.util.console.ConsoleWriter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Menu {
    private static String [] items = {"Список каналов", "Добавить канал", "Удалить канал", "Загрузить канал из файла", "Выбрать канал", "Выход"};
    private static String [] chanelItems = {"Программа телепередач", "Добавить программу", "Удалить программу", "Сортировка по времени", "Сортировка по названию", "Сохранить канал в файл", "Назад"};
    private static ConsoleWriter cw = new ConsoleWriter();
    private static ArrayList<Chanel> chanels;


    // Использование класса ConsoleReader
    private static ConsoleReader reader = new ConsoleReader();

    public void start() {
        Integer item = 0;
        do {
            print(items);
            item = reader.getInt();
        } while(checkItem(item));
    }

    public void initialize() {
        chanels = new ArrayList<Chanel>();
    }

    public void stop() {
        cw.println("Вы вышли из программы");
    }

    private void print(String [] menuItems) {
        Integer i = 0;
        for(String itemName : menuItems) {
            cw.print(i);
            cw.println(" - " + itemName);
            i++;
        }
        System.out.print("Выберите пункт меню: ");
    }

    private Boolean checkItem(Integer item) {
        switch(item) {
            case 0: showChanelList(); break;
            case 1: addChanel(); break;
            case 2: removeChanel(); break;
            case 3: laodChanel(); break;
            case 4: selectChanel(); break;
            case 5: return false;
        }
        return true;
    }

    private void showChanelList() {
        if(chanels.isEmpty()) {
            cw.println("Список пуст");
        } else {
            cw.println("Список каналов: ");
            for(int i = 0; i < chanels.size(); i++) {
                cw.print(i);
                cw.println(" - " + chanels.get(i).getTitle());
            }
        }
        reader.getString();
    }

    private void addChanel() {
        cw.print("Введите название канала: ");
        String title = reader.getString();
        Chanel chanel = new Chanel();
        chanel.setTitle(title);
        chanels.add(chanel);
        cw.println("Канал добавлен (" + title + ")");
        reader.getString();
    }

    private void removeChanel() {
        cw.print("Введите номер канала, который хотите удалить: ");
        int id = reader.getInt();
        if (!verifyIndex(chanels, id)) {
            cw.println("Неверный номер канала!");
            reader.getString();
            return;
        }
        Chanel removedChanel = chanels.get(id);
        chanels.remove(id);
        cw.println("Канал удален (" + removedChanel.getTitle() + ")");
        reader.getString();
    }

    private void selectChanel() {
        cw.print("Введите номер канала: ");
        int id = reader.getInt();
        if (!verifyIndex(chanels, id)) {
            cw.println("Неверный номер канала!");
            reader.getString();
            return;
        }
        Chanel chanel = chanels.get(id);
        cw.println("Выбран канал " + chanel.getTitle());
        Boolean ok = true;
        Integer item = 0;
        do {
            print(chanelItems);
            item = reader.getInt();
            switch(item) {
                case 0: showProgram(chanel); break;
                case 1: addProgram(chanel); break;
                case 2: removeProgram(chanel); break;
                case 3: sortByTime(chanel); break;
                case 4: sortByTitle(chanel); break;
                case 5: saveChanel(chanel); break;
                case 6: ok = false; break;
            }
        } while(ok);
    }

    private void showProgram(Chanel chanel) {
        cw.println("Программа телепередач");
        for(Program program : chanel.getProgram()) {
            cw.println(program);
        }
        reader.getString();
    }

    private void addProgram(Chanel chanel) {
        Program program = new Program();
        cw.print("Введите название программы: ");
        String title = reader.getString();
        cw.print("Введите время начала программы (ДД.ММ.ГГГГ чч:мм): ");
        String startTimeStr = reader.getString();
        cw.print("Введите время окончания программы (ДД.ММ.ГГГГ чч:мм): ");
        String endTimeStr = reader.getString();
        program.setTitle(title);
        program.setStartTime(DateTimeUtils.strToTime(startTimeStr));
        program.setEndTime(DateTimeUtils.strToTime(endTimeStr));
        if (program.getEndTime() == null || program.getStartTime() == null) {
            return;
        }
        chanel.getProgram().add(program);
    }

    private void removeProgram(Chanel chanel) {
        cw.print("Введите номер программы, которую хотите удалить: ");
        int id = reader.getInt();
        if (!verifyIndex(chanel.getProgram(), id)) {
            cw.println("Неверный номер программы!");
            reader.getString();
            return;
        }
        chanel.getProgram().remove(id);
        cw.println("Программа удалена");
        reader.getString();
    }

    private Boolean verifyIndex(ArrayList list, Integer index) {
        if ((list.size() - 1 ) < index) {
            return false;
        }
            return true;
    }

    private void saveChanel(Chanel chanel) {
        try {
            XMLIOUtil.writeChanelToXml(chanel.getTitle() + ".xml", chanel);
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void laodChanel() {
        Chanel chanel = new Chanel();
        cw.print("Имя файла: ");
        String filename = reader.getString();
        try {
            XMLIOUtil.readChanelFromXml(filename, chanel);
            chanels.add(chanel);
            cw.println("Канал " + chanel.getTitle() + " загружен");
        } catch (SAXException | IOException | ParserConfigurationException e) {
            cw.println("Канал из файла " + filename + " не был загружен");
        }
    }

    private void sortByTime(Chanel chanel) {
        chanel.getProgram().sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        cw.println("Программы канала " + chanel.getTitle() + " упорядочены по времени");
    }

    private void sortByTitle(Chanel chanel) {
        chanel.getProgram().sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
        cw.println("Программы канала " + chanel.getTitle() + " упорядочены по названию");
    }
}

