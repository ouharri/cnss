package com.macnss.dao;

import com.macnss.Libs.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VerificationAdministratorsCodesDao extends Model {
    public VerificationAdministratorsCodesDao() {
        super("verification_administrators_codes", new String[]{"administrator_id", "code_generated_at"});
    }

    public boolean create(int administrator_id, String code) {
        try {
            String query = "INSERT INTO verification_administrators_codes (administrator_id, verification_code) VALUES (?, ?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setDouble(1, administrator_id);
            preparedStatement.setString(2, code);

            return preparedStatement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String retrieveCode(int administrator_id) {
        try {
            String query = "SELECT vac.verification_code AS result" +
                    " FROM verification_administrators_codes vac" +
                    " INNER JOIN administrators ad ON vac.administrator_id = ad.administrator_id" +
                    " WHERE EXTRACT(EPOCH FROM (CURRENT_TIMESTAMP - vac.code_generated_at)) < 300" +
                    "  AND ad.administrator_id = ?" +
                    " ORDER BY vac.code_generated_at DESC" +
                    " LIMIT 1";
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, administrator_id);
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
