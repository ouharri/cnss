package com.macnss.dao;

import com.macnss.libs.Model;

public final class UserRoleDao extends Model {
    public UserRoleDao() {
        super("users_roles", new String[]{"user", "role"});
    }

}
