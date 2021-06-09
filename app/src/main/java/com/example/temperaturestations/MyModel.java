package com.example.temperaturestations;

public class MyModel {
    String name;
    String temp;
    String hum;
    Integer sig;
    Long unix;
    public MyModel(String name, String temp, String hum, Integer sig, Long unix) {
        this.name = name;
        this.temp = temp + " °C";
        this.hum = hum;
        this.sig = sig;
        this.unix = unix;
    }

    public String getName() {
        return name;
    }

    public String getTemp() {
        return temp;
    }

    public String getHum() {
        return hum;
    }

    public Integer getSig(){ return sig;}

    public Long getUnix() {
        return unix;
    }
}
