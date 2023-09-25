package com.macnss.app.Services;

import com.macnss.app.Models.Administrator;
import com.macnss.app.Models.AgentCNSS;
import com.macnss.app.Models.Patient;
import com.macnss.dao.AdministratorDao;
import com.macnss.dao.VerificationAdministratorsCodesDao;
import com.macnss.helpers.AuthenticationHelpers;

import java.util.Optional;

public class Authentication {

    private final Administrator administrator = new Administrator();
    private final AgentCNSS agentCNSS = new AgentCNSS();
    private final Patient patient = new Patient();


    /**
     * Constructs a new AuthenticationController.
     */
    public Authentication() {
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
//    public User register( String cnie, String firstName, String lastName,String email, String password, Gender gender, String phone) throws Exception {
//
//        user.setUser(
//                cnie,
//                firstName,
//                lastName,
//                gender,
//                email,
//                phone,
//                hlp.hashPassword(password)
//        );
//
//        try(UserDao userDao = new UserDao(user)) {
//            if (userDao.isExistedUser()) {
//                throw new Exception("User already exists");
//            } else {
//                    return userDao.create();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    /**
     * Pre Authenticates a Administrator.
     *
     * @param cnieOrEmailOrPhone The CNIE, email, or phone number used for authentication.
     * @param password           The user's password.
     * @return `code` if authentication is successful, `0` otherwise.
     */
    public boolean PreAdministratorAuthenticate(String cnieOrEmailOrPhone, String password) {

        try (AdministratorDao dao = new AdministratorDao()) {
            Optional<Administrator> adm = dao.get(cnieOrEmailOrPhone);
            if (adm.isPresent()) {
                Administrator administrator = adm.get();
                if (AuthenticationHelpers.checkPassword(password, administrator.getPassword())) {
                    int code = AuthenticationHelpers.generateRandomCode(6);
                    try (VerificationAdministratorsCodesDao dao2 = new VerificationAdministratorsCodesDao()) {
                        if (dao2.create(administrator.getAdministrator_id(), code)) {
                            new EmailService().send(administrator.getEmail(), "your code de verification", "Your code is : &" + code);
                        } else {
                            throw new RuntimeException("Error while creating verification code");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public boolean AdministratorAuthenticate(String code, String username, String password) {
        try (VerificationAdministratorsCodesDao dao = new VerificationAdministratorsCodesDao()) {
            return dao.isExistedCode(code, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a user by CNIE, email, or phone number.
     *
     * @param cnieOrEmailOrPhone The CNIE, email, or phone number of the user.
     * @return The user if found, or `null` if not found.
     * @throws RuntimeException If a database error occurs during the user retrieval.
     */
    private Administrator getUserByCnieOrEmail(String cnieOrEmailOrPhone) {
        try (AdministratorDao dao = new AdministratorDao()) {
            return dao.get(cnieOrEmailOrPhone).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private boolean userExists() {
//        try(AdministratorDao dao = new AdministratorDao()){
//            return dao.isExistedUser();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}