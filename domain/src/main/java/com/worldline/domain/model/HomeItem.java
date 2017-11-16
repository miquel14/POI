package com.worldline.domain.model;

import lombok.Data;

@Data
public class HomeItem {

    private int id;

    private String title;

    private String transport;

    private String address;

    private String email;

    private String description;

    private String phone;

    private String geocoord;
}
