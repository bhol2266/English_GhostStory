package com.bhola.english_ghoststory;

public class AudioModel {
    String name, duration, date, link;

    public AudioModel() {
    }

    public AudioModel(String name, String duration, String date, String link) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

