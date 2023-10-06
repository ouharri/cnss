package com.macnss.app.Models;

import com.macnss.Libs.orm.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Table(name = "company")
public class company implements smiyaMoa9ata{

    private UUID company_id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private String website;
}
