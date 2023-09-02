package com.example.advncd;

public class appmodel {
    private String photo;
    private  String topic;

    public String getPhotoid() {
        return photoid;
    }

    public void setPhotoid(String photoid) {
        this.photoid = photoid;
    }

    private  String photoid;


    public appmodel() {
    }

    public appmodel(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopics(String topics) {
        this.topic = topic;
    }
}
