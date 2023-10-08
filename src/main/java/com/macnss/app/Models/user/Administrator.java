package com.macnss.app.Models.user;

import com.macnss.app.Models.Abstract.employee;
import com.macnss.app.Enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.Map;

/**
 * Represents an Administrator user in the system, extending the 'employee' class.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Administrator extends employee {

    private int administrator_id;

    /**
     * Sets the Administrator's information, including personal details and administrator ID.
     *
     * @param cnie         The CNIE of the Administrator.
     * @param firstName    The first name of the Administrator.
     * @param lastName     The last name of the Administrator.
     * @param birthday     The birthday of the Administrator.
     * @param gender       The gender of the Administrator.
     * @param email        The email address of the Administrator.
     * @param phone        The phone number of the Administrator.
     * @param password     The password of the Administrator.
     * @param administrator_id The unique identifier for the Administrator.
     */


    /**
     * Retrieves Administrator user data as a map, including the administrator ID.
     *
     * @return A map containing Administrator user data.
     */
    public Map<String, Object> getAdministrator() {
        Map<String, Object> administrator = super.getUser();
        administrator.put("administrator_id", this.administrator_id);
        return administrator;
    }
}