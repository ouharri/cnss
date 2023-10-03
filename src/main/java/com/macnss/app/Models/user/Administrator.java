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

    public Map<String, Object> getAdministrator() {
        Map<String, Object> administrator = super.getUser();
        administrator.put("administrator_id", this.administrator_id);
        return administrator;
    }

}
