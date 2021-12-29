package com.tvprogram;


public class Run {
    private static Menu menu = new Menu();

    public static void main(String [] args) {
        menu.initialize();
        menu.start();
        menu.stop();
    }
}

