package com.example.demo.model;

/**
 * Created by huang on 2018/5/3.
 */
public class Foo {
    //String SSID;
    String Date;
    long SEQUENCECOUNT;
    String MAC;


    /*public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }*/

    public long getSEQUENCECOUNT() {
        return SEQUENCECOUNT;
    }
    public void setSEQUENCECOUNT(long SEQUENCECOUNT){
        this.SEQUENCECOUNT = SEQUENCECOUNT;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

}
