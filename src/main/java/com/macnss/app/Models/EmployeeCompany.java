package com.macnss.app.Models;

import com.macnss.Libs.orm.PrimaryKey;
import com.macnss.Libs.orm.Table;
import com.macnss.app.Enums.employeetype;
import com.macnss.app.Models.user.employees;
import com.macnss.database.dao.CompanyDao;
import com.macnss.database.dao.EmployeeDao;
import lombok.*;

import java.beans.JavaBean;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;


@ToString
@Table(name = "employees_company")
public class EmployeeCompany {

    public @PrimaryKey UUID id;
    public employees employee;
    public company company;
    public employeetype employee_type = null;
    public Date start_date = null;
    public Date end_date = null;
    public BigDecimal salary;
    public BigDecimal contribution;




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
        System.out.println(3);
        try {
            employee = new EmployeeDao().get(new Object[]{employee_id});
            if (employee == null) System.out.println("employee not exist");
        } catch (Exception e) {
            System.out.println("employee not exist");
        }
    }

    public void setCompany(Object company_id) {
        System.out.println(1);
        try {
            company = new CompanyDao().get(new Object[]{company_id});
            if (this.employee == null) System.out.println("company not exist");
        } catch (Exception e) {
            System.out.println("company not exist");
        }
    }

    public EmployeeCompany() {
    }

}
