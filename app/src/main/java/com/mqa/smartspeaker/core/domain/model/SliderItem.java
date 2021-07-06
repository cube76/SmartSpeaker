package com.mqa.smartspeaker.core.domain.model;

public class SliderItem {

    private int image;
    private String title;
    private String subtitle;
    private String next;
    private int state;

    public SliderItem(int image,String title,String subtitle,int state,String next){
        this.image = image;
        this.title = title;
        this.subtitle = subtitle;
        this.state = state;
        this.next = next;
    }

    public int getImage() {
        return image;
    }

    public int getState() {
        return state;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getNext() {
        return next;
    }
}
