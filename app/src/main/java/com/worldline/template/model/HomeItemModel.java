package com.worldline.template.model;

public class HomeItemModel {

    private int id;

    private String title;

    private String transport;

    private String address;

    private String email;

    private String description;

    private String phone;

    private String geoCoordinates;

    private String distanceInKm;

    private Boolean favorite = false;

    private Double longitude;

    private Double latitude;

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(String geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    public String getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(String distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setCoordinates(){
        String coordinates = getGeoCoordinates();
        String[] coordinatesList = coordinates.split(",");
        String latitude = coordinatesList[0];
        String longitude = coordinatesList[1];
        if (getLatitude() == null) {
            setLatitude(Double.parseDouble(latitude));
        }
        if (getLongitude() == null) {
            setLongitude(Double.parseDouble(longitude));
        }
    }
}
