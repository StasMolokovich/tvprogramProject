package com.tvprogram.util.console;

import java.util.Scanner;

public class ConsoleReader {

    private Scanner scanner = new Scanner(System.in, "Cp866");

    public Integer getInt() {
        Integer val = -1;
        String strVal = scanner.nextLine();
        try {
            val = Integer.valueOf(strVal);
        } catch(NumberFormatException e) {

        }
        return val;
    }

    public String getString() {
        return scanner.nextLine();
    }
}

