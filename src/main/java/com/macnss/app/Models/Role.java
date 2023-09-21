package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class Role{

    protected int id;
    protected com.macnss.app.Enums.Role role;

    protected List<User> users = new ArrayList<User>();

    public void setRole(int id, String roleTitle) {
        this.id = id;
        this.role = com.macnss.app.Enums.Role.valueOf(roleTitle);
    }

    public void hasUsers(List<User> users) {
        this.users = users;
    }
}
