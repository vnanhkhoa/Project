package com.vnak.test;

import java.io.Serializable;

public class Song implements Serializable {
    private int img;
    private String song;
    private String singer;

    public Song() {
    }

    public Song(int img, String song, String singer) {
        this.img = img;
        this.song = song;
        this.singer = singer;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
