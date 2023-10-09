package com.macnss.app.Models;

import com.macnss.Libs.orm.Table;
import com.macnss.Libs.orm.PrimaryKey;
import com.macnss.Libs.orm.schema;
import lombok.Data;

import java.util.UUID;

@PrimaryKey
@Table(name = "company")
public @Data class company implements schema {

    public @PrimaryKey UUID id;
    public String name;
    public String address;
    public String city;
    public String country;
    public String postalCode;
    public String phone;
    public String email;
    public String website;
    public String psw_hash;



    public company() {
    }

    public company(String name, String address, String city, String country, String postalCode, String phone, String email, String website) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
        this.website = website;
    }

}