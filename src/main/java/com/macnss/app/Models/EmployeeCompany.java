package com.macnss.app.Models;

import com.macnss.Libs.orm.Table;
import com.macnss.app.Models.user.employees;
import lombok.Data;

import java.sql.Date;

@Data
@Table(name = "employees_company")
public class EmployeeCompany
{
    com.macnss.app.Models.company company;
    employees employee;
    double salary;
    double contribution = 0.2;
    Date start_date;
    Date end_date;

}
