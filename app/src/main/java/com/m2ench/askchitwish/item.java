package com.m2ench.askchitwish;

/**
 * Created by viswanath on 17-01-2017.
 */

public class item {
    private int id;
    private String catg_id;
    private String topic_name;
    private String short_desc;
    private String ingredients;
    private String ingredients1;
    private String method;
    private String method1;
    private String order_no;
    private String added;
    private String updated;
    private String times_viewed;
    private String images;
    private String audio;
    private String video;
    private String active;
    private String extra;
    private String diplay_order;

    public item(int id, String catg_id, String topic_name, String short_desc) {
        this.id = id;
        this.catg_id = catg_id;
        this.topic_name = topic_name;
        this.short_desc = short_desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCatg_id() {
        return catg_id;
    }

    public void setCatg_id(String catg_id) {
        this.catg_id = catg_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIngredients1() {
        return ingredients1;
    }

    public void setIngredients1(String ingredients1) {
        this.ingredients1 = ingredients1;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod1() {
        return method1;
    }

    public void setMethod1(String method1) {
        this.method1 = method1;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTimes_viewed() {
        return times_viewed;
    }

    public void setTimes_viewed(String times_viewed) {
        this.times_viewed = times_viewed;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getDiplay_order() {
        return diplay_order;
    }

    public void setDiplay_order(String diplay_order) {
        this.diplay_order = diplay_order;
    }
}
