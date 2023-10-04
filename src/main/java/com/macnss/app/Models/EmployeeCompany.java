package com.macnss.app.Models;

import com.macnss.app.Models.user.Employee;
import lombok.Data;

@Data
public class EmployeeCompany
{
    int employeeCompanyId;
    Company company;
    Employee employee;
    double salary;
    double contribution = 0.2;

}
