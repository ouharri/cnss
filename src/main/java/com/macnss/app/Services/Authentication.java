package com.macnss.app.Services;

import com.macnss.app.Models.user.Administrator;
import com.macnss.app.Models.user.AgentCNSS;
import com.macnss.dao.AdministratorDao;
import com.macnss.dao.AgentCNSSDao;
import com.macnss.dao.VerificationAdministratorsCodesDao;
import com.macnss.dao.VerificationAgentCNSSCodesDao;
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
        try (AdministratorDao dao = new AdministratorDao(new Administrator())) {
            Optional<Administrator> adminOptional = dao.get(username);
            if (adminOptional.isPresent()) {
                Administrator administrator = adminOptional.get();
                if (AuthenticationHelpers.checkPassword(password, administrator.getPassword())) {
                    int code = AuthenticationHelpers.generateRandomCode(6);
                    try (VerificationAdministratorsCodesDao codeDao = new VerificationAdministratorsCodesDao()) {
                        if (codeDao.create(administrator.getAdministrator_id(), AuthenticationHelpers.hashPassword(String.valueOf(code)))) {
                            EmailService emailService = new EmailService(administrator.getEmail(), "Authentication Admin Code");
                            Thread emailThread = new Thread(emailService);
                            emailService.setText("Your code is: " + code);
                            emailThread.start();
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
        try (AdministratorDao dao = new AdministratorDao(new Administrator())) {
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

    /**
     * Pre-authenticates an AgentCNSS.
     *
     * @param username The username (CNIE, email, or phone number) used for authentication.
     * @param password The user's password.
     * @return true if pre-authentication is successful, false otherwise.
     */
    public boolean preAuthenticateAgentCNSS(String username, String password) {
        try (AgentCNSSDao dao = new AgentCNSSDao(new AgentCNSS())) {
            Optional<AgentCNSS> agentOptional = dao.get(username);
            if (agentOptional.isPresent()) {
                AgentCNSS agentCNSS = agentOptional.get();
                if (AuthenticationHelpers.checkPassword(password, agentCNSS.getPassword())) {
                    int code = AuthenticationHelpers.generateRandomCode(6);
                    try (VerificationAgentCNSSCodesDao codeDao = new VerificationAgentCNSSCodesDao()) {
                        if (codeDao.create(agentCNSS.getAgent_cns_id(), AuthenticationHelpers.hashPassword(String.valueOf(code)))) {
                            EmailService emailService = new EmailService(agentCNSS.getEmail(), "Authentication Agent Code");
                            Thread emailThread = new Thread(emailService);
                            emailService.setText("Your code is: " + code);
                            emailThread.start();
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
            throw new RuntimeException("Error during AgentCNSS pre-authentication", e);
        }
    }

    /**
     * Authenticates an AgentCNSS.
     *
     * @param code     The verification code.
     * @param username The username (CNIE, email, or phone number) used for authentication.
     * @param password The user's password.
     * @return true if authentication is successful, false otherwise.
     */
    public boolean authenticateAgentCNSS(String code, String username, String password) {
        try (AgentCNSSDao dao = new AgentCNSSDao(new AgentCNSS())) {
            Optional<AgentCNSS> agentOptional = dao.get(username);
            if (agentOptional.isPresent()) {
                AgentCNSS agentCNSS = agentOptional.get();
                if (AuthenticationHelpers.checkPassword(password, agentCNSS.getPassword())) {
                    try (VerificationAgentCNSSCodesDao codeDao = new VerificationAgentCNSSCodesDao()) {
                        return AuthenticationHelpers.checkPassword(code, codeDao.retrieveCode(agentCNSS.getAgent_cns_id()));
                    } catch (Exception e) {
                        throw new RuntimeException("Error while verifying AgentCNSS code", e);
                    }
                }
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error during AgentCNSS authentication", e);
        }
    }
}