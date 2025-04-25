package com.example.infertility.ItemModels;

public class ProductItem {
    private int imageResId;
    private String title;
    private String subtitle;
    private String price;
    private String ratings;

    public ProductItem(int imageResId, String title, String subtitle, String ratings, String price) {
        this.imageResId = imageResId;
        this.title = title;
        this.subtitle = subtitle;
        this.price = price;
        this.ratings = ratings;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }
}
