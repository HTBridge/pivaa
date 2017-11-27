package com.htbridge.pivaa.handlers.about;


import com.htbridge.pivaa.Configuration;

public class AboutRecord {
    private int id;
    private String name;
    private String description;
    private Configuration config = new Configuration();

    public AboutRecord() {

    }

    public AboutRecord(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Record [id=" + id + ", name=" + name + ", description=" + description
                + "]";
    }
}
