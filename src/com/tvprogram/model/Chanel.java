package com.tvprogram.model;

import java.util.ArrayList;

public class Chanel {
    private String title;
    private ArrayList<Program> program;

    public Chanel() {
        program = new ArrayList<Program>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Program> getProgram() {
        return program;
    }

    public void setProgram(ArrayList<Program> program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Канал " +  title;
    }
}

