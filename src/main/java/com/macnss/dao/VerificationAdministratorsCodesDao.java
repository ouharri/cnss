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

    public boolean isExistedCode(String code, String username, String password){
        try {
            String query = "SELECT EXISTS (" +
                    "  SELECT 1 FROM verification_administrators_codes vac " +
                    "  INNER JOIN administrators ad " +
                    "  ON vac.administrator_id = ad.administrator_id " +
                    "  WHERE verification_code = ? " +
                    "  AND (ad.email = ? OR ad.cnie = ?)" +
                    "  AND ad.pwd_hash = ? " +
                    "  AND EXTRACT(SECOND FROM (CURRENT_TIMESTAMP - vac.code_generated_at)) < 300" +
                    "  ORDER BY vac.code_generated_at DESC" +
                    ") AS result";

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
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
