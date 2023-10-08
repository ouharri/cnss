package com.macnss.app.Models;

import com.macnss.Libs.orm.AutoGenerateGetSet;
import com.macnss.Libs.orm.PrimaryKey;
import com.macnss.Libs.orm.Table;
import com.macnss.app.Enums.employeetype;
import com.macnss.app.Models.user.employees;
import com.macnss.database.dao.CompanyDao;
import com.macnss.database.dao.EmployeeDao;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;


@PrimaryKey
@Table(name = "employees_company")
public @Data() class EmployeeCompany {

    @Getter(AccessLevel.NONE) employees  employee;
    @Getter(AccessLevel.NONE) company company;
    employeetype employee_type = null;
    Date start_date = null;
    Date end_date = null;
    @AutoGenerateGetSet
    BigDecimal salary;
    BigDecimal contribution;


    public EmployeeCompany() {
    }

    public EmployeeCompany(employees employee, company company, employeetype employee_type, Date start_date, Date end_date, BigDecimal salary, BigDecimal contribution) {
        this.employee = employee;
        this.company = company;
        this.employee_type = employee_type;
        this.start_date = start_date;
        this.end_date = end_date;
        this.salary = salary;
        this.contribution = contribution;
    }

    public void setEmployee(UUID employee_id) {
        try {
            employee = new EmployeeDao().get(new Object[]{employee_id});
            if (employee == null) System.out.println("employee not exist");
        } catch (Exception e) {
            System.out.println("employee not exist");
        }
    }

    public void setCompany(UUID company_id) {
        try {
            System.out.println(1);
            company = new CompanyDao().get(new Object[]{company_id});
            if (employee == null) System.out.println("company not exist");
        } catch (Exception e) {
            System.out.println("company not exist");
        }
    }

    public void setEmployee_type(String employee_type) {
        this.employee_type = employeetype.valueOf(employee_type);
    }
}
