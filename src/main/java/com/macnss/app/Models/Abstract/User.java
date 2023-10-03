package com.macnss.app.Models.Abstract;

import com.macnss.app.Enums.Gender;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class representing a user with common attributes and methods.
 */
@Data
public abstract class User {

    protected String cnie = null;
    protected String firstName = null;
    protected String lastName = null;
    protected String email = null;
    protected Date birthday = null;
    protected String phone = null;
    protected Gender gender = null;
    protected String password = null;
    protected String address = null;
    protected String city = null;
    protected String country = null;
    protected String postal_code = null;
    protected java.sql.Timestamp delete_at = null;

    /**
     * Retrieves user data as a map.
     *
     * @return A map containing user data.
     */
    public Map<String, Object> getUser() {
        Map<String, Object> userData = new HashMap<>();
        if (this.cnie != null) userData.put("cnie", this.cnie);
        if (this.firstName != null) userData.put("first_name", this.firstName);
        if (this.lastName != null) userData.put("last_name", this.lastName);
        if (this.birthday != null) userData.put("birthday", this.birthday);
        if (this.email != null) userData.put("email", this.email);
        if (this.phone != null) userData.put("phone", this.phone);
        if (this.gender != null) userData.put("gender", this.gender);
        if (this.password != null) userData.put("pwd_hash", this.password);
        if (this.address != null) userData.put("address", this.address);
        if (this.city != null) userData.put("city", this.city);
        if (this.country != null) userData.put("country", this.country);
        if (this.postal_code != null) userData.put("postalCode", this.postal_code);
        return userData;
    }

    /**
     * Sets user information.
     *
     * @param cnie      The CNIE of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param birthday  The birthday of the user.
     * @param gender    The gender of the user.
     * @param email     The email address of the user.
     * @param phone     The phone number of the user.
     * @param password  The password of the user.
     */
    public void setUser(String cnie, String firstName, String lastName, Date birthday, Gender gender, String email, String phone, String password) {
        this.cnie = cnie;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = Gender.valueOf(String.valueOf(gender));
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    /**
     * Sets basic user information without birthday, gender, or password.
     *
     * @param cnie      The CNIE of the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     */
    public void setUser(String cnie, String firstName, String lastName) {
        this.cnie = cnie;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the full name of the user.
     *
     * @return The full name of the user.
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Gets the full address of the user.
     *
     * @return The full address of the user.
     */
    public String getFullAddress() {
        return this.address + ",\n" + this.city + ", " + this.postal_code + ",\n" + this.country;
    }

    /**
     * Calculates and returns the age of the user based on their birthday.
     *
     * @return The age of the user as a double value (in years).
     */
    public double getAge() {
        LocalDate curDate = LocalDate.now();
        Period period = Period.between(this.birthday.toLocalDate(), curDate);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        return years + (months / 12.0) + (days / 365.0);
    }
}