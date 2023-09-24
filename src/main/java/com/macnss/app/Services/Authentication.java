package com.macnss.app.Services;

import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Abstract.User;
import com.macnss.dao.UserDao;
import com.macnss.helpers.AuthenticationHelpers;

public class Authentication {

    private final User user;
    private final AuthenticationHelpers hlp = new AuthenticationHelpers();

    /**
     * Constructs a new AuthenticationController.
     */
    public Authentication() {
        this.user = new User();
    }

    /**
     * Registers a new user.
     *
     * @param cnie      The CNIE (user identifier).
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The user's password.
     * @param gender    The gender of the user.
     * @param phone     The user's phone number.
     * @return The newly registered user.
     * @throws Exception             If a database error occurs during user registration.
     */
    public User register( String cnie, String firstName, String lastName,String email, String password, Gender gender, String phone) throws Exception {

        user.setUser(
                cnie,
                firstName,
                lastName,
                gender,
                email,
                phone,
                hlp.hashPassword(password)
        );

        try(UserDao userDao = new UserDao(user)) {
            if (userDao.isExistedUser()) {
                throw new Exception("User already exists");
            } else {
                    return userDao.create();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Authenticates a user.
     *
     * @param cnieOrEmailOrPhone The CNIE, email, or phone number used for authentication.
     * @param password           The user's password.
     * @return `true` if authentication is successful, `false` otherwise.
     */
    public boolean PreAuthenticate(String cnieOrEmailOrPhone, String password) {

        User user = getUserByCnieOrEmailOrPhone(cnieOrEmailOrPhone);

        if (user != null) {
            return hlp.checkPassword(password, user.getPassword());
        }

        return false;
    }

    public boolean Authenticate (String username,String password,String code) {
        return true;
    }

    /**
     * Retrieves a user by CNIE, email, or phone number.
     *
     * @param cnieOrEmailOrPhone The CNIE, email, or phone number of the user.
     * @return The user if found, or `null` if not found.
     * @throws RuntimeException If a database error occurs during the user retrieval.
     */
    public User getUserByCnieOrEmailOrPhone(String cnieOrEmailOrPhone) {

        try(UserDao userDao = new UserDao()){
            return userDao.find(cnieOrEmailOrPhone);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean userExists() {
        try(UserDao userDao = new UserDao(user)) {
            return userDao.isExistedUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}