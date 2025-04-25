package com.example.infertility.ItemModels;

public class DiscussionItem {


    private int imageResId;
    private String discussionTitle;

    public DiscussionItem(int imageResId, String discussionTitle) {
        this.imageResId = imageResId;
        this.discussionTitle = discussionTitle;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public String getDiscussionTitle() {
        return discussionTitle;
    }

    public void setDiscussionTitle(String discussionTitle) {
        this.discussionTitle = discussionTitle;
    }
}
