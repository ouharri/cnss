package com.macnss.app.Models.user;

import com.macnss.app.Enums.AgentStatus;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Abstract.employee;

import java.sql.Date;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents an Agent for CNSS (Caisse Nationale de Sécurité Sociale), extending the 'employee' class.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentCNSS extends employee {

    private int agent_cns_id = 0;
    private AgentStatus status = null;

    /**
     * Sets the Agent's information, including personal details, agent ID, and status.
     *
     * @param cnie     The CNIE (Carte Nationale d'Identité Electronique) of the Agent.
     * @param firstName The first name of the Agent.
     * @param lastName  The last name of the Agent.
     * @param birthday  The birthday of the Agent.
     * @param gender    The gender of the Agent.
     * @param email    The email address of the Agent.
     * @param phone    The phone number of the Agent.
     * @param password The password of the Agent.
     * @param agent_cns_id The unique identifier for the Agent.
     * @param status   The status of the Agent.
     */
    public void setAgent(String cnie, String firstName, String lastName, Date birthday, Gender gender, String email, String phone, String password, int agent_cns_id, AgentStatus status) {
        super.setUser(cnie, firstName, lastName, birthday, gender, email, phone, password);
        this.agent_cns_id = agent_cns_id;
        this.status = status;
    }

    /**
     * Retrieves Agent data as a map, including the agent ID and status.
     *
     * @return A map containing Agent data.
     */
    public Map<String, Object> getAgentCNSS() {
        Map<String, Object> agents = super.getUser();
        if (this.agent_cns_id != 0) agents.put("agent_id", this.agent_cns_id);
        if (this.status != null) agents.put("status", this.status);
        return agents;
    }
}