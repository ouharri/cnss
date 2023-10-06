package com.macnss.app.Models.user;

import com.macnss.Libs.orm.Table;
import com.macnss.app.Enums.Gender;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

import com.macnss.app.Models.smiyaMoa9ata;
import lombok.Data;

/**
 * Represents an Employee, extending the 'employee' class.
 */
@Data
@Table(name = "employees")
public class employees extends com.macnss.app.Models.Abstract.employee implements smiyaMoa9ata {

    private UUID employee_id;

    /**
     * Sets the Employee's information, including personal details and employee ID.
     *
     * @param cnie        The CNIE (Carte Nationale d'Identité Electronique) of the Employee.
     * @param firstName   The first name of the Employee.
     * @param lastName    The last name of the Employee.
     * @param birthday    The birthday of the Employee.
     * @param gender      The gender of the Employee.
     * @param email       The email address of the Employee.
     * @param phone       The phone number of the Employee.
     * @param password    The password of the Employee.
     * @param employee_id The unique identifier for the Employee.
     */
    public void setEmployee(String cnie, String firstName, String lastName, Date birthday, Gender gender, String email, String phone, String password, UUID employee_id) {
        super.setUser(cnie, firstName, lastName, birthday, gender, email, phone, password);
        this.employee_id = employee_id;
    }

    /**
     * Retrieves Employee data as a map, including the employee ID.
     *
     * @return A map containing Employee data.
     */
    public Map<String, Object> getEmployee() {
        Map<String, Object> employee = super.getUser();
        employee.put("employee_id", this.employee_id);
        return employee;
    }
}