package com.macnss.app.Models.user;

import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Abstract.User;
import com.macnss.Libs.orm.schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.Map;

/**
 * Represents a Patient, extending the 'User' class.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Patient extends User implements schema {

    private String matriculate;

    /**
     * Sets the Patient's information, including personal details and matriculate number.
     *
     * @param cnie      The CNIE (Carte Nationale d'Identité Electronique) of the Patient.
     * @param firstName The first name of the Patient.
     * @param lastName  The last name of the Patient.
     * @param birthday  The birthday of the Patient.
     * @param gender    The gender of the Patient.
     * @param email     The email address of the Patient.
     * @param phone     The phone number of the Patient.
     * @param password  The password of the Patient.
     * @param matriculate The matriculate number of the Patient.
     */
    public void setPatient(String cnie, String firstName, String lastName, Date birthday, Gender gender, String email, String phone, String password, String matriculate) {
        super.setUser(cnie, firstName, lastName, birthday, gender, email, phone, password);
        this.matriculate = matriculate;
    }

    /**
     * Retrieves Patient data as a map, including the matriculate number.
     *
     * @return A map containing Patient data.
     */
    public Map<String, Object> getPatient() {
        Map<String, Object> patient = super.getUser();
        patient.put("matriculate", this.matriculate);
        return patient;
    }
}