package com.macnss.app.Models;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Company {

    private int company_id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private String website;

    public void setCompany(int company_id, String name, String address, String city, String country, String postalCode, String phone, String email, String website) {
        this.company_id = company_id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
        this.website = website;
    }

    public Map<String,Object> getCompany() {
        Map<String,Object> companyData = new HashMap<>();
        companyData.put("company_id", this.company_id);
        companyData.put("name", this.name);
        companyData.put("address", this.address);
        companyData.put("city", this.city);
        companyData.put("country", this.country);
        companyData.put("postalCode", this.postalCode);
        companyData.put("phone", this.phone);
        companyData.put("email", this.email);
        companyData.put("website", this.website);
        return companyData;
    }
}
