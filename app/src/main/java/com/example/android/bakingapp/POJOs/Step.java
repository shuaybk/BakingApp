package com.example.android.bakingapp.POJOs;

import java.io.Serializable;

public class Step implements Serializable {

    private int id;
    private String shortDescr;
    private String fullDescr;
    private String videoUrl;
    private String thumbnailUrl;

    public Step(int id, String shortDescr, String fullDescr, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescr = shortDescr;
        this.fullDescr = fullDescr;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescr() {
        return shortDescr;
    }

    public void setShortDescr(String shortDescr) {
        this.shortDescr = shortDescr;
    }

    public String getFullDescr() {
        return fullDescr;
    }

    public void setFullDescr(String fullDescr) {
        this.fullDescr = fullDescr;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
