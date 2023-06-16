package com.sound.demo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LocationModal extends RealmObject {
    @PrimaryKey
    private long id;
    private String lat_;
    private String long_;

    public LocationModal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLat_() {
        return lat_;
    }

    public void setLat_(String lat_) {
        this.lat_ = lat_;
    }

    public String getLong_() {
        return long_;
    }

    public void setLong_(String long_) {
        this.long_ = long_;
    }

    @Override
    public String toString() {
        return "LocationModal{" +
                "lat_='" + lat_ + '\'' +
                ", long_='" + long_ + '\'' +
                '}';
    }
}
