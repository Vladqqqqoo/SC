package com.vladqqqqoo.model;

import java.io.Serializable;

abstract public class Packet implements Serializable {
    private String code;

    public Packet(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
