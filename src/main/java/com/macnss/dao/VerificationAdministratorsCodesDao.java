package com.macnss.dao;

import com.macnss.Libs.Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class VerificationAdministratorsCodesDao extends Model {
    public VerificationAdministratorsCodesDao() {
        super("verification_administrators_codes", new String[]{"administrator_id", "code_generated_at"});
    }

    public boolean create(int administrator_id, int code){
        try {
            String query = "INSERT INTO verification_administrators_codes (administrator_id, verification_code) VALUES (?, ?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setDouble(1, administrator_id);
            preparedStatement.setInt(2, code);

            return preparedStatement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isExistedCode(String code, double administrator_id, String password){
        try {
            String query = "SELECT EXISTS (" +
                    "  SELECT 1 FROM verification_administrators_codes vac " +
                    "  INNER JOIN administrators ad " +
                    "  ON vac.administrator_id = ad.administrator_id " +
                    "  WHERE verification_code = ? " +
                    "  AND vac.administrator_id = ? " +
                    "  AND ad.pwd_hash = ? " +
                    "  AND CURRENT_TIMESTAMP - vac.code_generated_at < INTERVAL 5 MINUTE" +
                    ") AS result";

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            preparedStatement.setDouble(2, administrator_id);
            preparedStatement.setString(3, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("result");
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
