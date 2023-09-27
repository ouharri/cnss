package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.AgentStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerificationAgentCNSSCodesDao extends Model {
    public VerificationAgentCNSSCodesDao() {
        super("verification_agent_cnss_codes", new String[]{"agent_id", "code_generated_at"});
    }

    public boolean create(int agent_id, String code) {
        try {
            String query = "INSERT INTO verification_agents_cnss_codes (agent_id, verification_code) VALUES (?, ?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, agent_id);
            preparedStatement.setString(2, code);

            return preparedStatement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String retrieveCode(int agent_id) {
        try {
            String query = "SELECT vac.verification_code AS result" +
                    " FROM verification_agents_cnss_codes vac" +
                    " INNER JOIN agents_cnss ac ON vac.agent_id = ac.agent_id" +
                    " WHERE EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - vac.code_generated_at)) < 300" +
                    " AND ac.agent_id = ?" +
                    " AND ac.status = ?" +
                    " ORDER BY vac.code_generated_at DESC" +
                    " LIMIT 1";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, agent_id);
            preparedStatement.setObject(2, AgentStatus.ACTIVE, java.sql.Types.OTHER);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("result");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
