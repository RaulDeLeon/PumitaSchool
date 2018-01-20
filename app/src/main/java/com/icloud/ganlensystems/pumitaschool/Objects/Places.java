package com.icloud.ganlensystems.pumitaschool.Objects;

/**
 * Created by drewermerc on 23/11/17.
 */

public class Places {
    private String address;
    private String description;
    private String image;
    private Double latitude;
    private String link;
    private Double longitude;
    private String name;
    private String phone;
    private String schedule;
    private String snippet;

    public Places() {
        this.address = "";
        this.description = "";
        this.image = "";
        this.latitude = 0.0;
        this.link = "";
        this.longitude = 0.0;
        this.name = "";
        this.phone = "";
        this.schedule = "";
        this.snippet = "";
    }

    public Places(String address, String description, String image, Double latitude, String link, Double longitude, String name, String phone, String schedule, String snippet) {
        this.address = address;
        this.description = description;
        this.image = image;
        this.latitude = latitude;
        this.link = link;
        this.longitude = longitude;
        this.name = name;
        this.phone = phone;
        this.schedule = schedule;
        this.snippet = snippet;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getSnippet() { return snippet; }

    public String getLink() { return link; }

    public String getImage() {
        return image;
    }
}
