package com.example.mahirhasan.registrationandlogin;


public class Holiday {
    private String name;
    private String date;
    private String category;


    public Holiday(String name, String date, String category) {
        this.name = name;
        this.date = date;
        this.category = category;
    }

    public String getName() {

        return name;

    }


    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }
}
