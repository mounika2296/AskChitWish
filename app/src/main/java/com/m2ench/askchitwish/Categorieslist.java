package com.m2ench.askchitwish;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by viswanath on 18-01-2017.
 */

public class Categorieslist implements Parcelable {
    private String name;
    private String display_name;
    private String active;
    private String image;
    private String description;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Creator<Categorieslist> getCREATOR() {
        return CREATOR;
    }

    protected Categorieslist(Parcel in) {
        name = in.readString();
        display_name = in.readString();
        active = in.readString();
        image = in.readString();
        description = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(display_name);
        dest.writeString(active);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Categorieslist> CREATOR = new Creator<Categorieslist>() {
        @Override
        public Categorieslist createFromParcel(Parcel in) {
            return new Categorieslist(in);
        }

        @Override
        public Categorieslist[] newArray(int size) {
            return new Categorieslist[size];
        }
    };
}
