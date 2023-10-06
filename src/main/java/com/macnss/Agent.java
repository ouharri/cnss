package com.macnss;

import com.macnss.app.Models.company;
import com.macnss.app.Models.EmployeeCompany;
import com.macnss.app.Models.user.employees;
import com.macnss.database.dao.CompanyDao;
import com.macnss.database.dao.EmployeeDao;
import com.macnss.database.dao.employeesCompanyDao;

import java.util.UUID;

public class Agent {


    private Agent() {
    }

    public static void main(String[] args) {


        company comp = new CompanyDao().get(new Object[]{UUID.fromString("071e6b59-05b2-4d9c-9f1e-60f4a082e23d")});
        employees emp = new EmployeeDao().get(new Object[]{UUID.fromString("e6815846-378e-4853-976b-a1be5133c383")});

        EmployeeCompany empCop = new EmployeeCompany();

        empCop.setCompany(comp);
        empCop.setEmployee(emp);
        empCop.setSalary(1000);
        empCop.setContribution(0.2);
        empCop.setStart_date(new java.sql.Date(System.currentTimeMillis()));

        System.out.println(comp.toString());
        System.out.println(emp.toString());
        System.out.println(new employeesCompanyDao().setObject(empCop).save());
//        new SigningAgentCNS();
    }

}
