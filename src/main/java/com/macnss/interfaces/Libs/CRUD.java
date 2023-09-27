package com.macnss.interfaces.Libs;

import java.util.Map;
import java.sql.*;


public interface CRUD {
    String create(Map<String, Object> data) throws SQLException;

    Map<String, Object> read(String[] ids);

    Map<String, Object> read(String[] columnNames, String[] values);

    boolean update(Map<String, Object> data, String[] ids);

    boolean delete(String[] ids);
}