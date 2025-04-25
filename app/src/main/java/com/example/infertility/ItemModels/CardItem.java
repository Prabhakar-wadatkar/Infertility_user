package com.example.infertility.ItemModels;

public class CardItem {
    int imageRes;
    String title;
    String subtitle;

    public CardItem(int imageRes, String title, String subtitle) {
        this.imageRes = imageRes;
        this.title = title;
        this.subtitle = subtitle;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
