package com.macnss.database.dao;

import com.macnss.Libs.orm.Model;
import com.macnss.app.Models.user.employees;

/**
 * Data Access Object (DAO) for managing Employee entities.
 */
public class EmployeeDao extends Model<employees> {

    public EmployeeDao() {
        _primaryKey.add("employee_id");
    }

}
