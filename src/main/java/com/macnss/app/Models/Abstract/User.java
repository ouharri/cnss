package com.macnss.app.Models.Abstract;

import com.macnss.app.Enums.AgentStatus;
import com.macnss.app.Enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class User {

    protected String cnie;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Date birthday;
    protected String phone;
    protected Gender gender;
    protected String password;
    protected java.sql.Timestamp delete_at = null;

    public Map<String, Object> getUser() {

        Map<String, Object> UserData = new HashMap<>();

        UserData.put("cnie", this.cnie);
        UserData.put("first_name", this.firstName);
        UserData.put("last_name", this.lastName);
        UserData.put("birthday", this.birthday);
        UserData.put("email", this.email);
        UserData.put("phone", this.phone);
        UserData.put("gender", this.gender);
        UserData.put("password", this.password);

        return UserData;
    }

    public void
    setUser(String cnie, String firstName, String lastName, Date birthday, Gender gender, String email, String phone, String password) {
        this.cnie = cnie;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = Gender.valueOf(String.valueOf(gender));
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public void setUser(String cnie, String firstName, String lastName) {
        this.cnie = cnie;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}