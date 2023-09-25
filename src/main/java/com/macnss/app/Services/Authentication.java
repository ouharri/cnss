package com.macnss.app.Services;

import com.macnss.app.Models.Administrator;
import com.macnss.dao.AdministratorDao;
import com.macnss.dao.VerificationAdministratorsCodesDao;
import com.macnss.helpers.AuthenticationHelpers;

import java.util.Optional;

/**
 * Provides authentication services for users.
 */
public class Authentication {

    /**
     * Constructs a new Authentication service.
     */
    public Authentication() {
    }

    /**
     * Pre-authenticates an administrator.
     *
     * @param username The username (CNIE, email, or phone number) used for authentication.
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean preAuthenticateAdministrator(String username, String password) {
        try (AdministratorDao dao = new AdministratorDao()) {
            Optional<Administrator> adminOptional = dao.get(username);
            if (adminOptional.isPresent()) {
                Administrator administrator = adminOptional.get();
                if (AuthenticationHelpers.checkPassword(password, administrator.getPassword())) {
                    int code = AuthenticationHelpers.generateRandomCode(6);
                    try (VerificationAdministratorsCodesDao codeDao = new VerificationAdministratorsCodesDao()) {
                        if (codeDao.create(administrator.getAdministrator_id(), AuthenticationHelpers.hashPassword( String.valueOf(code) ))) {
                            EmailService emailService = new EmailService();
                            emailService.send(administrator.getEmail(), "Your verification code", "Your code is: " + code);
                            return true;
                        } else {
                            throw new RuntimeException("Error while creating verification code");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during administrator authentication", e);
        }
    }

    /**
     * Authenticates an administrator.
     *
     * @param code     The verification code.
     * @param username The username (CNIE, email, or phone number) used for authentication.
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean authenticateAdministrator(String code, String username, String password) {
        try (AdministratorDao dao = new AdministratorDao()) {
            Optional<Administrator> adminOptional = dao.get(username);
            if (adminOptional.isPresent()) {
                Administrator administrator = adminOptional.get();
                if (AuthenticationHelpers.checkPassword(password, administrator.getPassword())) {
                    try (VerificationAdministratorsCodesDao codeDao = new VerificationAdministratorsCodesDao()) {
                        return AuthenticationHelpers.checkPassword(code, codeDao.retrieveCode(administrator.getAdministrator_id()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error while verifying administrator code", e);
                    }
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error during administrator authentication", e);
        }
    }
}