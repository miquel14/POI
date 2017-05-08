package com.worldline.data.entity.vo;

import io.realm.RealmObject;

/**
 * StringVo class to wrap
 */
public class StringVo extends RealmObject {

    private String string;

    public StringVo() {
    }

    public StringVo(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

}
