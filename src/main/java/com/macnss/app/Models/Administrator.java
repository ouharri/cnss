package com.macnss.app.Models;

import com.macnss.app.Models.Abstract.employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends employee {

    private int administrator_id;

    public Map<String, String> getAdministrator(){
        Map<String, String> administrator = new HashMap<>();

        administrator.put("administrator_id", String.valueOf(this.administrator_id));
        administrator.put("cnie", this.cnie);
        administrator.put("first_name", this.first_name);
        administrator.put("last_name", this.last_name);
        administrator.put("email", this.email);
        administrator.put("phone", this.phone);
        administrator.put("gender", this.gender.toString());
        administrator.put("password", this.password);

        return administrator;
    }

}
