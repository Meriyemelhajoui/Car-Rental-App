package com.example.javaproject;

public class Feedback {
    int id;
    String cin;
    String description;

    public Feedback(int id,String cin,String description) {
        this.id = id;
        this.cin = cin;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
