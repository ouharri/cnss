package com.macnss.app.Models.user;

import com.macnss.app.Models.Abstract.employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends employee {

    private int administrator_id;

    public Map<String, Object> getAdministrator(){
        Map<String, Object> administrator = new HashMap<>();

        administrator.put("administrator_id", this.administrator_id);
        administrator.put("cnie", this.cnie);
        administrator.put("first_name", this.firstName);
        administrator.put("last_name", this.lastName);
        administrator.put("email", this.email);
        administrator.put("phone", this.phone);
        administrator.put("gender", this.gender);
        administrator.put("password", this.password);

        return administrator;
    }

}
