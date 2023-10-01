package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.AgentStatus;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.AgentCNSS;
import com.macnss.interfaces.Dao.Dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing AgentCNSS entities.
 */
public class AgentCNSSDao extends Model implements Dao<AgentCNSS> {

    private final AgentCNSS agentCnss;

    public AgentCNSSDao(AgentCNSS agent) {
        super("agents_cnss", new String[]{"agent_id"});
        agentCnss = agent;
    }

    /**
     * Reads an AgentCNSS entity based on its unique identifier.
     *
     * @return The read AgentCNSS entity or null if not found.
     */
    @Override
    public AgentCNSS read() {
        Map<String, Object> agentData = super.read(new Object[]{agentCnss.getAgent_cns_id()});

        if (agentData != null) {
            agentCnss.setUser(
                    (String) agentData.get("cnie"),
                    (String) agentData.get("first_name"),
                    (String) agentData.get("last_name"),
                    Date.valueOf((String) agentData.get("birthday")),
                    Gender.valueOf((String) agentData.get("gender")),
                    (String) agentData.get("email"),
                    (String) agentData.get("phone"),
                    (String) agentData.get("password")
            );

            agentCnss.setAgent_cns_id((Integer) agentData.get("agent_id"));
            agentCnss.setStatus(AgentStatus.valueOf((String) agentData.get("status")));

            return agentCnss;
        } else {
            return null;
        }
    }

    /**
     * Saves the AgentCNSS entity to the database.
     *
     * @return An optional containing the saved AgentCNSS entity, or an empty optional if there's an error.
     * @throws SQLException If an SQL error occurs during the save operation.
     */
    @Override
    public Optional<AgentCNSS> save() throws SQLException {
        if (super.create(agentCnss.getAgentCNSS()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(agentCnss);
        }
    }

    /**
     * Retrieves an AgentCNSS entity by its email.
     *
     * @param email The email of the AgentCNSS to retrieve.
     * @return An optional containing the AgentCNSS if found, or an empty optional if not found.
     */
    @Override
    public Optional<AgentCNSS> get(String email) {
        Map<String, Object> agentData = super.read(new String[]{"email", "status::text"}, new String[]{email, AgentStatus.ACTIVE.toString()});

        if (agentData == null) {
            return Optional.empty();
        }

        agentCnss.setUser(
                (String) agentData.get("cnie"),
                (String) agentData.get("first_name"),
                (String) agentData.get("last_name"),
                (Date) agentData.get("birthday"),
                Gender.valueOf((String) agentData.get("gender")),
                (String) agentData.get("email"),
                (String) agentData.get("phone"),
                (String) agentData.get("pwd_hash")
        );

        agentCnss.setAgent_cns_id((Integer) agentData.get("agent_id"));

        return Optional.of(agentCnss);
    }

    /**
     * Retrieves all AgentCNSS entities.
     *
     * @return A list of all AgentCNSS entities.
     */
    public List<AgentCNSS> getAll() {
        List<AgentCNSS> agentsCNSS = new ArrayList<>();

        List<Map<String, Object>> agents = super.retrieveAll();

        if (agents.isEmpty()) return null;

        agents.forEach((agentData) -> {
            AgentCNSS agentCNSS = new AgentCNSS();

            agentCNSS.setUser(
                    (String) agentData.get("cnie"),
                    (String) agentData.get("first_name"),
                    (String) agentData.get("last_name"),
                    (Date) agentData.get("birthday"),
                    Gender.valueOf((String) agentData.get("gender")),
                    (String) agentData.get("email"),
                    (String) agentData.get("phone"),
                    (String) agentData.get("password")
            );
            agentCNSS.setAgent_cns_id((Integer) agentData.get("agent_id"));
            agentCNSS.setStatus(AgentStatus.valueOf((String) agentData.get("status")));

            agentsCNSS.add(agentCNSS);
        });

        return agentsCNSS;
    }

    /**
     * Creates a new AgentCNSS entity in the database.
     *
     * @param entity The AgentCNSS entity to be created.
     * @return An optional containing the created AgentCNSS entity, or an empty optional if there's an error.
     * @throws SQLException if a database error occurs during creation.
     */
    @Override
    public Optional<AgentCNSS> create(AgentCNSS entity) throws SQLException {
        if (super.create(entity.getAgentCNSS()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    /**
     * Updates an existing AgentCNSS entity in the database.
     *
     * @param agent The AgentCNSS entity to be updated.
     * @return An optional containing the updated AgentCNSS entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<AgentCNSS> update(AgentCNSS agent) {
        if (super.update(agent.getAgentCNSS(), new String[]{String.valueOf(agent.getAgent_cns_id())})) {
            return Optional.of(agent);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Finds all AgentCNSS entities based on a search criteria.
     *
     * @param criteria The search criteria.
     * @return A list of AgentCNSS entities that match the criteria.
     */
    @Override
    public List<AgentCNSS> find(String criteria) {
        List<AgentCNSS> agentsCNSS = new ArrayList<>();

        List<Map<String, Object>> agents = super.readAll(new String[]{criteria});

        if (agents.isEmpty()) return agentsCNSS;

        agents.forEach((agentData) -> {
            AgentCNSS agentCNSS = new AgentCNSS();

            agentCNSS.setUser(
                    (String) agentData.get("cnie"),
                    (String) agentData.get("first_name"),
                    (String) agentData.get("last_name"),
                    Date.valueOf((String) agentData.get("birthday")),
                    Gender.valueOf((String) agentData.get("gender")),
                    (String) agentData.get("email"),
                    (String) agentData.get("phone"),
                    (String) agentData.get("password")
            );
            agentCNSS.setAgent_cns_id((Integer) agentData.get("agent_id"));

            agentsCNSS.add(agentCNSS);
        });

        return agentsCNSS;
    }

    /**
     * Deletes an AgentCNSS entity from the database.
     *
     * @param agent The AgentCNSS entity to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
    @Override
    public boolean delete(AgentCNSS agent) {
        return super.delete(new String[]{String.valueOf(agent.getAgentCNSS())});
    }
}