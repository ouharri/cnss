package com.macnss.database.dao;

import com.macnss.Libs.orm.Model;
import com.macnss.Libs.orm.Table;
import com.macnss.app.Models.EmployeeCompany;

@Table(name = "employees_company")
public class employeesCompanyDao extends Model<EmployeeCompany> {

    public employeesCompanyDao() {
        _primaryKey.add("employee");
        _primaryKey.add("company");
        _primaryKey.add("end_date");
    }

}