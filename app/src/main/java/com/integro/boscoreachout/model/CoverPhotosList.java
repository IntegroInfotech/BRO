package com.integro.boscoreachout.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoverPhotosList {

    @SerializedName("bosco_news")
    private ArrayList<CoverPhotos> coverPhotosArrayList;

    private String success;

    private String message;

    public ArrayList<CoverPhotos> getCoverPhotosArrayList() {
        return coverPhotosArrayList;
    }

    public void setCoverPhotosArrayList(ArrayList<CoverPhotos> coverPhotosArrayList) {
        this.coverPhotosArrayList = coverPhotosArrayList;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
