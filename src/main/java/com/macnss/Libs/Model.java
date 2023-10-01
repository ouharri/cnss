package com.macnss.Libs;

import com.macnss.Core.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The `Model` class provides a generic model for interacting with a database table. It implements CRUD (Create, Read, Update, Delete) operations
 * and supports transactions. This class should be extended by specific models for individual database tables.
 */
public class Model implements AutoCloseable, com.macnss.interfaces.Libs.Model {
    protected Connection connection;
    protected String _table;
    protected String[] _primaryKey = {"id"};
    protected String _foreignKey = null;
    protected Boolean _softDelete = false;
    private boolean inTransaction = false;

    public Model(String tableName, String[] primaryKey) {
        this.connection = database.getConnection();
        this._table = tableName;
        if (primaryKey != null && primaryKey.length > 0) {
            this._primaryKey = primaryKey;
        }
    }


    /**
     * Begins a database transaction. Call this method before performing a series of database operations
     * that should be treated as a single transaction. This allows for rolling back changes in case of errors.
     *
     * @throws SQLException If a database access error occurs.
     */
    public void beginTransaction() throws SQLException {
        if (!inTransaction) {
            this.connection.setAutoCommit(false);
            this.inTransaction = true;
        }
    }

    /**
     * Commits the current database transaction. This should be called after successfully completing a series
     * of database operations within a transaction.
     *
     * @throws SQLException If a database access error occurs.
     */
    public void commitTransaction() throws SQLException {
        if (inTransaction) {
            this.connection.commit();
            this.connection.setAutoCommit(true);
            this.inTransaction = false;
        }
    }

    /**
     * Rolls back the current database transaction. This should be called in case of an error or when you want
     * to discard changes made within the current transaction.
     *
     * @throws SQLException If a database access error occurs.
     */
    public void rollbackTransaction() throws SQLException {
        if (inTransaction) {
            this.connection.rollback();
            this.connection.setAutoCommit(true);
            this.inTransaction = false;
        }
    }

    /**
     * Retrieves all records from the associated database table.
     *
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    @Override
    public List<Map<String, Object>> retrieveAll() {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + this._table;

            if (this._softDelete) {
                query += " WHERE delete_at IS NULL";
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    rowData.put(columnName, resultSet.getObject(columnName));
                }

                resultList.add(rowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * Searches for records in the associated database table that match a given keyword in specified columns.
     *
     * @param keyword The search keyword to match.
     * @param columns The columns in which to perform the search.
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    public List<Map<String, Object>> search(String keyword, String[] columns) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM ").append(this._table).append(" WHERE ");

            if (this._softDelete) {
                queryBuilder.append("delete_at IS NULL AND (");
            } else {
                queryBuilder.append("(");
            }

            for (int i = 0; i < columns.length; i++) {
                queryBuilder.append(columns[i]).append(" LIKE ?");
                if (i < columns.length - 1) {
                    queryBuilder.append(" OR ");
                }
            }
            queryBuilder.append(")");

            PreparedStatement preparedStatement = this.connection.prepareStatement(queryBuilder.toString());

            for (int i = 0; i < columns.length; i++) {
                preparedStatement.setObject(i + 1, "%" + keyword + "%");
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    rowData.put(columnName, resultSet.getObject(columnName));
                }

                resultList.add(rowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * Creates a new record in the associated database table with the provided data.
     *
     * @param data A map of column names and their corresponding values for the new record.
     * @return The primary key value (e.g., ID) of the newly created record, or null if the operation fails.
     * @throws SQLException If a database access error occurs.
     */
    @Override
    public Object create(Map<String, Object> data) throws SQLException {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            columns.append(entry.getKey()).append(",");
            values.append("?").append(",");
        }

        columns.setLength(columns.length() - 1);
        values.setLength(values.length() - 1);

        String query = "INSERT INTO " + _table + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";
        PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        int index = 1;
        for (Object value : data.values()) {
            if (value instanceof Enum<?>) {
                preparedStatement.setObject(index++, value, Types.OTHER);
            } else {
                preparedStatement.setObject(index++, value);
            }
        }

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getObject(1);
            } else {
                return data.get(this._primaryKey[0]);
            }
        }
        return null;
    }

    /**
     * Reads a single record from the associated database table based on the specified primary key values.
     *
     * @param ids An array of primary key values used to identify the record.
     * @return A map representing the retrieved record's data, or null if the record is not found.
     */
    @Override
    public Map<String, Object> read(Object[] ids) {
        try {
            StringBuilder whereClause = new StringBuilder();
            for (int i = 0; i < _primaryKey.length; i++) {
                whereClause.append(_primaryKey[i]).append(" = ?");
                if (i < _primaryKey.length - 1) {
                    whereClause.append(" AND ");
                }
            }

            String query = "SELECT * FROM " + this._table + " WHERE " + whereClause.toString();

            if (this._softDelete) {
                query += " AND delete_at IS NULL";
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            for (int i = 0; i < ids.length; i++) {
                if (ids[i] instanceof Enum<?>) {
                    preparedStatement.setObject(i + 1, ids[i], Types.OTHER);
                } else {
                    preparedStatement.setObject(i + 1, ids[i]);
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();

                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    rowData.put(columnName, resultSet.getObject(columnName));
                }

                return rowData;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Reads a single record from the associated database table based on a specific column value.
     *
     * @param columnNames The name of the column to search.
     * @param values      The value to search for in the specified column.
     * @return A map representing the retrieved record's data, or null if the record is not found.
     */
    @Override
    public Map<String, Object> read(String[] columnNames, Object[] values) {
        try {
            if (columnNames.length != values.length) {
                throw new IllegalArgumentException("The columnNames and values arrays must have the same length.");
            }

            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ").append(this._table).append(" WHERE ");

            for (int i = 0; i < columnNames.length; i++) {
                if (i > 0) {
                    queryBuilder.append(" AND ");
                }
                queryBuilder.append(columnNames[i]).append(" = ?");
            }

            if (this._softDelete) {
                queryBuilder.append(" AND delete_at IS NULL");
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(queryBuilder.toString());

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Enum<?>) {
                    preparedStatement.setObject(i + 1, values[i], Types.OTHER);

                } else {
                    preparedStatement.setObject(i + 1, values[i]);
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    String column = metaData.getColumnName(i);
                    rowData.put(column, resultSet.getObject(column));
                }

                return rowData;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    /**
     * Retrieves the count of records in the associated database table that match a specific column value.
     *
     * @param WhereColumnName The name of the column to search.
     * @param value           The value to search for in the specified column.
     * @return The count of records matching the specified column value.
     */
    public int getColumnCount(String WhereColumnName, String value) {
        System.out.println(this._softDelete);
        try {
            String query = "SELECT count(*) AS count FROM " + this._table + " WHERE " + WhereColumnName + " = ?";

            if (this._softDelete) {
                query += " AND delete_at IS NULL";
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, value);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Retrieves all records from the associated database table that match a specific column value.
     *
     * @param columnName The name of the column to search.
     * @param value      The value to search for in the specified column.
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    public List<Map<String, Object>> readAll(String columnName, Object value) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            String query = "SELECT * FROM " + this._table + " WHERE " + columnName + " = ?";

            if (this._softDelete) {
                query += " AND delete_at IS NULL";
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            if (value instanceof Enum<?>) {
                preparedStatement.setObject(1, value, Types.OTHER);
            } else {
                preparedStatement.setObject(1, value);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String column = metaData.getColumnName(i);
                    rowData.put(column, resultSet.getObject(column));
                }

                resultList.add(rowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * Retrieves all records from the associated database table based on the specified primary key values.
     *
     * @param ids An array of primary key values used to identify the records.
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    public List<Map<String, Object>> readAll(Object[] ids) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            StringBuilder whereClause = new StringBuilder();
            for (int i = 0; i < _primaryKey.length; i++) {
                whereClause.append(_primaryKey[i]).append(" = ?");
                if (i < _primaryKey.length - 1) {
                    whereClause.append(" AND ");
                }
            }

            String query = "SELECT * FROM " + this._table + " WHERE " + whereClause.toString();

            if (this._softDelete) {
                query += " AND delete_at IS NULL";
            }

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            for (int i = 0; i < ids.length; i++) {
                if (ids[i] instanceof Enum<?>) {
                    preparedStatement.setObject(i + 1, ids[i], Types.OTHER);

                } else {
                    preparedStatement.setObject(i + 1, ids[i]);
                }
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    rowData.put(columnName, resultSet.getObject(columnName));
                }

                resultList.add(rowData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * Updates records in the associated database table with the provided data based on specified primary key values.
     *
     * @param data A map of column names and their corresponding values for the update.
     * @param ids  An array of primary key values used to identify the records to be updated.
     * @return True if the update operation is successful; otherwise, false.
     */
    @Override
    public boolean update(Map<String, Object> data, Object[] ids) {
        try {
            StringBuilder setClause = new StringBuilder();
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                setClause.append(entry.getKey()).append(" = ?,");
            }
            setClause.setLength(setClause.length() - 1);

            StringBuilder whereClause = new StringBuilder();
            for (int i = 0; i < _primaryKey.length; i++) {
                whereClause.append(_primaryKey[i]).append(" = ?");
                if (i < _primaryKey.length - 1) {
                    whereClause.append(" AND ");
                }
            }

            String query = "UPDATE " + _table + " SET " + setClause.toString() + " WHERE " + whereClause.toString();
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            int index = 1;
            for (Object value : data.values()) {
                if (value instanceof Enum<?>) {
                    preparedStatement.setObject(index++, value, Types.OTHER);
                } else {
                    preparedStatement.setObject(index++, value);
                }
            }

            for (Object id : ids) {
                if (id instanceof Enum<?>) {
                    preparedStatement.setObject(index++, id, Types.OTHER);
                } else {
                    preparedStatement.setObject(index++, id);
                }
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes records from the associated database table based on specified primary key values.
     *
     * @param ids An array of primary key values used to identify the records to be deleted.
     * @return True if the delete operation is successful; otherwise, false.
     */
    @Override
    public boolean delete(Object[] ids) {
        try {
            StringBuilder whereClause = new StringBuilder();

            int ids_length = ids.length;
            int primaryKey_length = _primaryKey.length;

            for (int i = 0; i < ids_length && i < primaryKey_length; i++) {
                whereClause.append(_primaryKey[i]).append(" = ?");
                if (i < ids_length - 1 && i < primaryKey_length - 1) {
                    whereClause.append(" AND ");
                }
            }

            String query = "DELETE FROM " + _table + " WHERE " + whereClause.toString();
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            for (int i = 0; i < ids_length && i < primaryKey_length; i++) {
                if (ids[i] instanceof Enum<?>) {
                    preparedStatement.setObject(i + 1, ids[i], Types.OTHER);
                } else {
                    preparedStatement.setObject(i + 1, ids[i]);
                }
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Performs a soft delete operation on the records identified by the given IDs.
     * Soft delete sets the "delete_at" column to the current timestamp.
     *
     * @param ids The array of IDs of records to be soft-deleted.
     * @return True if the records were successfully soft-deleted; otherwise, false.
     */
    public boolean softDelete(Object[] ids) {
        try {

            String query = "UPDATE " + _table + " SET " + "delete_at = ?" + " WHERE ";

            StringBuilder whereClause = new StringBuilder();
            for (int i = 0; i < _primaryKey.length; i++) {
                whereClause.append(_primaryKey[i]).append(" = ?");
                if (i < _primaryKey.length - 1) {
                    whereClause.append(" AND ");
                }
            }

            query += whereClause.toString();
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            preparedStatement.setTimestamp(1, currentTimestamp);

            for (int i = 0; i < ids.length; i++) {
                if (ids[i] instanceof Enum<?>) {
                    preparedStatement.setObject(i + 2, ids[i], Types.OTHER);
                } else {
                    preparedStatement.setObject(i + 2, ids[i]);
                }
            }

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void close() {
        this.connection = database.closeConnection();
    }
}