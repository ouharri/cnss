package com.macnss.app.Models;

import com.macnss.app.Enums.Month;
import com.macnss.app.Models.user.Employee;
import lombok.Data;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Salary {

    EmployeeCompany employeeCompany;
    Month month;
    int year;
    int Workday;

    public void setSalary( EmployeeCompany employeeCompany, Month month, int year, int Workday) {
        this.employeeCompany = employeeCompany;
        this.month = month;
        this.year = year;
        this.Workday = Workday;
    }

    public  Map<String,Object> getSalary(){
        Map<String,Object> salaryData = new HashMap<>();
        salaryData.put("employee_company", this.employeeCompany);
        salaryData.put("work_month", this.month);
        salaryData.put("work_year", this.year);
        salaryData.put("work_day", this.Workday);
        return salaryData;
    }

}
