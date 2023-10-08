package com.macnss.app.Models;

import com.macnss.Libs.orm.Table;
import com.macnss.Libs.orm.PrimaryKey;
import com.macnss.Libs.orm.smiyaMoa9ata;
import lombok.Data;

import java.util.UUID;

@Table(name = "company")
@PrimaryKey
public @Data class company implements smiyaMoa9ata {

    private UUID id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private String website;

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
