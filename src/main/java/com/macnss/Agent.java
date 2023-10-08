package com.macnss;

import com.macnss.app.Enums.employeetype;
import com.macnss.app.Models.company;
import com.macnss.app.Models.EmployeeCompany;
import com.macnss.app.Models.user.employees;
import com.macnss.database.dao.CompanyDao;
import com.macnss.database.dao.EmployeeDao;
import com.macnss.database.dao.employeesCompanyDao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

public class Agent {


    private Agent() {
    }

    public static void main(String[] args) {


        company company = new company(
                "orivato",
                "zer9tono",
                "casablanca",
                "57867",
                "20000",
                "0666666666",
                "DJJFE@FEF.COM3",
                "uhukh");

//        employees emp = new employees();
//        emp.setFirst_name("mohamed");
//        emp.setLast_name("mohamed");
//        emp.setPhone("0666666666");
//        emp.setEmail("jhjkh@jhj");
//        emp.getBirthday("1999-09-09");
        company comdao = new CompanyDao().get(new Object[]{UUID.fromString("635ca9b4-d6cf-4bf1-b1d5-8346f4ef57fd")});
        employees em = new EmployeeDao().get(new Object[]{UUID.fromString("e9cd95ec-b80c-456f-806f-f0bee7f85a37")});
        em.setFirst_name("outman");

        EmployeeCompany empComp = new EmployeeCompany(
                em,
                comdao,
                employeetype.ADMINISTRATOR,
                Date.valueOf("2020-09-09"),
                null,
                BigDecimal.valueOf(67576.0000),
                BigDecimal.valueOf(500)
        );


        employeesCompanyDao employeesCompanyDaoDao = new employeesCompanyDao();
        EmployeeCompany employeesCompany = employeesCompanyDaoDao.get(new Object[]{UUID.fromString("bce4f68a-7eb7-4ab9-bd36-e8eefb2312e9")});
        empComp.setId(UUID.fromString("4bdafd2d-3839-474b-826f-2ed388893a88"));

        System.out.println(new employeesCompanyDao().where("employee",UUID.fromString("e9cd95ec-b80c-456f-806f-f0bee7f85a37")).count());

        new EmployeeCompany();


//        try (employeesCompanyDao dao = new employeesCompanyDao()) {
//            System.out.println(dao.getAll());
//        } catch (Exception e) {
//            System.out.println("company not inserted");
//        }

////        new SigningAgentCNS();
    }

}
