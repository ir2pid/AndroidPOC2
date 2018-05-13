package com.noisyninja.androidlistpoc.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "me")
public class Me {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "meId")
    private int meId;

    @TypeConverters(DataConverter.class)
    @SerializedName("name")
    @Expose
    private Name name;

    @TypeConverters(DataConverter.class)
    @SerializedName("picture")
    @Expose
    private Picture picture;

    @Expose
    @ColumnInfo(name = "page")
    private int page;


    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public int getMeId() {
        return meId;
    }

    public void setMeId(int meId) {
        this.meId = meId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
