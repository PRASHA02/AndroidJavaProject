package com.example.gst.models;

public class Billing {
    String name ;
    String phoneNo ;
    String description;
    String amount;
    String quan;
    private String key;


     String spin;
    public Billing() {
    }


    public Billing(String name, String phoneNo, String description, String amount, String quan,String spin) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.description = description;
        this.amount = amount;
        this.quan= quan;
        this.spin = spin;
    }

    public String getSpin() {
        return spin;
    }

    public void setSpin(String spin) {
        this.spin = spin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quantity) {
        this.quan = quantity;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

}

