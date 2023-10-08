package com.macnss.Libs.orm;

import com.macnss.Core.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

import com.macnss.Libs.dbutils.QueryRunner;
import com.macnss.Libs.dbutils.ResultSetHandler;
import com.macnss.Libs.dbutils.handlers.BeanHandler;
import com.macnss.Libs.dbutils.handlers.BeanListHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The `Model` class provides a generic model for interacting with a database table. It implements CRUD (Create, Read, Update, Delete) operations
 * and supports transactions. This class should be extended by specific models for individual database tables.
 *
 * @param <T> The type of model associated with the database table.
 */
public abstract class Model<T> implements AutoCloseable, ModelInterface<T> {
    private Connection conn = database.getConnection();
    private final QueryRunner _run = new QueryRunner();
    private boolean inTransaction = false;

    private T _object = null;
    private final Class<T> _class;
    private final Field[] _fields;

    protected String _table;
    protected Queue<Object> _primaryKey = new LinkedList<>();
    protected final Queue<Object> _foreignKey = new LinkedList<>();
    protected Boolean _softDelete = false;

    private final StringBuilder _query = new StringBuilder();
    private final Queue<Object> _queryParam = new LinkedList<>();

    @SuppressWarnings("unchecked")
    public Model() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = superClass.getActualTypeArguments()[0];

        _class = (Class<T>) type;
        _fields = _class.getDeclaredFields();

        if (_class.isAnnotationPresent(Table.class)) _table = _class.getAnnotation(Table.class).name().toLowerCase();

        if (_table == null) _table = _class.getName();

        if (_class.isAnnotationPresent(PrimaryKey.class))
            _primaryKey = new LinkedList<>(Arrays.asList(_class.getAnnotation(PrimaryKey.class).key()));

    }

    /**
     * Associates a model object with the current instance.
     *
     * @param object The model object to associate.
     * @return The current Model instance.
     */
    @Override
    public Model<T> setObject(T object) {
        _object = object;
        return this;
    }

    /**
     * Begins a database transaction.
     *
     * @return The current Model instance.
     */
    @Override
    public Model<T> beginTransaction() {
        if (!inTransaction) try {
            this.conn.setAutoCommit(false);
            this.inTransaction = true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Commits the current database transaction.
     *
     * @return The current Model instance.
     */
    @Override
    public Model<T> commitTransaction() {
        if (inTransaction) try {
            this.conn.commit();
            this.conn.setAutoCommit(true);
            this.inTransaction = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Rolls back the current database transaction.
     *
     * @return The current Model instance.
     */
    @Override
    public Model<T> rollbackTransaction() {
        if (inTransaction) try {
            this.conn.rollback();
            this.conn.setAutoCommit(true);
            this.inTransaction = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /**
     * Retrieves all records from the associated database table.
     *
     * @return A list of model objects representing the records.
     */
    @Override
    public List<T> getAll() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ").append(_table);
            ResultSetHandler<List<T>> q = new BeanListHandler<>(_class);

            if (_softDelete) queryBuilder.append(" WHERE delete_at IS NULL");

            return _run.query(this.conn, queryBuilder.toString(), q);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a single record from the associated database table based on the specified primary key values.
     *
     * @param value An array of primary key values.
     * @return The model object representing the retrieved record, or null if not found.
     */
    @Override
    public T get(Object[] value) {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder whereClause = new StringBuilder();

            {
                int i = 0;
                for (Object p : _primaryKey) {
                    whereClause.append(p).append(" = ?");
                    if (i++ < _primaryKey.size() - 1) {
                        whereClause.append(" AND ");
                    }
                }
            }

            String query = "SELECT * FROM " + _table + " WHERE " + whereClause;
            ResultSetHandler<T> q = new BeanHandler<>(_class);

            if (this._softDelete) {
                query += " AND delete_at IS NULL";
            }

            return _run.query(this.conn, query, q, value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the associated model object as a new record in the database.
     *
     * @return The model object representing the newly created record.
     */
    @Override
    public T save() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            ResultSetHandler<T> q = new BeanHandler<>(_class);
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(_table).append(" (");
            StringBuilder valuesBuilder = new StringBuilder(") VALUES (");

            int fieldAllowed = 0;

            for (Field field : _fields) {
                field.setAccessible(true);

                if (field.get(_object) != null) fieldAllowed++;

                field.setAccessible(false);
            }

            Object[] values = new Object[fieldAllowed];
            int index = 0;

            for (Field field : _fields) {
                field.setAccessible(true);
                Object value = field.get(_object);

                if (value != null) {
                    if (value instanceof smiyaMoa9ata) {
                        value = value.getClass().getDeclaredMethod("getId").invoke(value);
                    }

                    values[index++] = value;
                    valuesBuilder.append("?");
                    queryBuilder.append(field.getName());

                    if (index < fieldAllowed) {
                        queryBuilder.append(", ");
                        valuesBuilder.append(", ");
                    }
                }

                field.setAccessible(false);
            }


            queryBuilder.append(valuesBuilder).append(")");

            if (this._softDelete) queryBuilder.append(" WHERE delete_at IS NULL");

            return _run.insert(this.conn, queryBuilder.toString(), q, values);
        } catch (SQLException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * insert the record in the database.
     *
     * @param obj The model object to insert into a database.
     * @return The model object representing the newly created record.
     */
    @Override
    public T insert(T obj) {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            ResultSetHandler<T> q = new BeanHandler<>(_class);
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO ").append(_table).append(" (");
            StringBuilder valuesBuilder = new StringBuilder(") VALUES (");

            int fieldAllowed = 0;

            for (Field field : _fields) {
                field.setAccessible(true);

                if (field.get(_object) != null) fieldAllowed++;

                field.setAccessible(false);
            }

            Object[] values = new Object[fieldAllowed];
            int index = 0;

            for (Field field : _fields) {
                field.setAccessible(true);
                Object value = field.get(obj);

                if (value != null) {
                    values[index++] = value;
                    queryBuilder.append(field.getName());
                    valuesBuilder.append("?");
                    if (index < fieldAllowed) {
                        queryBuilder.append(", ");
                        valuesBuilder.append(", ");
                    }
                }

                field.setAccessible(false);
            }


            queryBuilder.append(valuesBuilder).append(")");

            if (this._softDelete) queryBuilder.append(" WHERE delete_at IS NULL");

            return _run.insert(this.conn, queryBuilder.toString(), q, values);
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a WHERE clause to filter query results based on a column and its value.
     *
     * @param key   The name of the column.
     * @param value The value to compare.
     * @return The current Model instance with the WHERE clause added.
     */
    @Override
    public Model<T> where(String key, Object value) {
        if (!_query.isEmpty()) _query.setLength(0);
        if (!_queryParam.isEmpty()) _queryParam.clear();

        _query.append(" WHERE ").append(key).append(" = ? ");
        _queryParam.add(value);

        return this;
    }

    /**
     * Constructs a WHERE clause to filter query results based on a column, a custom comparison operator, and a value.
     *
     * @param key      The name of the column.
     * @param operator The custom comparison operator (e.g., "=", "<").
     * @param value    The value to compare.
     * @return The current Model instance with the WHERE clause added.
     */
    @Override
    public Model<T> where(String key, String operator, Object value) {
        if (!_query.isEmpty()) _query.setLength(0);
        if (!_queryParam.isEmpty()) _queryParam.clear();

        _query.append(" WHERE ").append(key).append(" ").append(operator).append(" ? ");
        _queryParam.add(value);

        return this;
    }

    /**
     * Constructs an AND clause to further filter query results based on a column and its value.
     *
     * @param key   The name of the column.
     * @param value The value to compare.
     * @return The current Model instance with the AND clause added.
     */
    @Override
    public Model<T> and(String key, Object value) {
        if (_query.isEmpty()) return this;

        _query.append(" AND ").append(key).append(" = ? ");
        _queryParam.add(value);

        return this;
    }

    /**
     * Constructs an AND clause to further filter query results based on a column, a custom comparison operator, and a value.
     *
     * @param key      The name of the column.
     * @param operator The custom comparison operator (e.g., "=", "<").
     * @param value    The value to compare.
     * @return The current Model instance with the AND clause added.
     */
    @Override
    public Model<T> and(String key, String operator, Object value) {
        if (_query.isEmpty()) return this;

        _query.append(" AND ").append(key).append(" ").append(operator).append(" ? ");
        _queryParam.add(value);

        return this;
    }

    /**
     * Constructs an OR clause to further filter query results based on a column and its value.
     *
     * @param key   The name of the column.
     * @param value The value to compare.
     * @return The current Model instance with the OR clause added.
     */
    @Override
    public Model<T> or(String key, Object value) {
        if (_query.isEmpty()) return this;

        _query.append(" OR ").append(key).append(" = ? ").append(value);
        _queryParam.add(value);

        return this;
    }

    /**
     * Constructs an OR clause to further filter query results based on a column, a custom comparison operator, and a value.
     *
     * @param key      The name of the column.
     * @param operator The custom comparison operator (e.g., "=", "<").
     * @param value    The value to compare.
     * @return The current Model instance with the OR clause added.
     */
    @Override
    public Model<T> or(String key, String operator, Object value) {
        if (_query.isEmpty()) return this;

        _query.append(" OR ").append(key).append(" ").append(operator).append(" ? ").append(value);
        _queryParam.add(value);

        return this;
    }

    /**
     * Constructs a LIKE clause to search query results based on a column and a value pattern.
     *
     * @param key   The name of the column.
     * @param value The value to search for.
     * @return The current Model instance with the LIKE clause added.
     */
    @Override
    public Model<T> like(String key, Object value) {
        if (!_query.isEmpty()) _query.setLength(0);

        _query.append(" WHERE ").append(key).append(" LIKE ? ");
        _queryParam.add(value);

        return this;
    }

    /**
     * Limits the maximum number of records to return in the query result.
     *
     * @param limit The maximum number of records to return.
     * @return The current Model instance with the LIMIT clause added.
     */
    @Override
    public Model<T> limit(int limit) {
        if (_query.isEmpty()) return this;

        _query.append(" LIMIT ").append(limit);

        return this;
    }

    /**
     * Sets the number of records to skip before starting to return results in the query result.
     *
     * @param offset The number of records to skip.
     * @return The current Model instance with the OFFSET clause added.
     */
    @Override
    public Model<T> offset(int offset) {
        if (_query.isEmpty()) return this;

        _query.append(" OFFSET ").append(offset);

        return this;
    }

    /**
     * Specifies the column and sorting direction for ordering query results.
     *
     * @param key       The column name for sorting.
     * @param direction The sorting direction (ascending or descending).
     * @return The current Model instance with the ORDER BY clause added.
     */
    @Override
    public Model<T> orderBy(String key, String direction) {
        if (_query.isEmpty()) return this;

        _query.append(" ORDER BY ").append(key).append(" ").append(direction);

        return this;
    }

    /**
     * Retrieves records from the associated database table based on the constructed query.
     *
     * @return A list of model objects representing the retrieved records.
     */
    @Override
    public List<T> find() {
        if (_query.isEmpty()) return new ArrayList<>();
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ").append(_table).append(_query);
            ResultSetHandler<List<T>> q = new BeanListHandler<>(_class);

            if (_softDelete) queryBuilder.append(" AND delete_at IS NULL");

            return _run.query(this.conn, queryBuilder.toString(), q, _queryParam.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a single record from the associated database table based on the constructed query.
     *
     * @return The model object representing the retrieved record, or null if not found.
     */
    @Override
    public T findOne() {
        if (_query.isEmpty()) return null;
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ").append(_table).append(_query);
            ResultSetHandler<T> q = new BeanHandler<>(_class);

            if (_softDelete) queryBuilder.append(" AND delete_at IS NULL");

            return _run.query(this.conn, queryBuilder.toString(), q);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the associated model object and saves the changes to the database.
     *
     * @return `true` if at least one field was updated, `false` otherwise.
     */
    @Override
    public boolean update() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            boolean atLeastOneFieldUpdated = false;
            List<Object> values = new ArrayList<>();
            StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(_table).append(" SET ");

            for (Field field : _fields) {
                field.setAccessible(true);

                Object value = field.get(_object);

                if (value != null) {
                    if (atLeastOneFieldUpdated) {
                        queryBuilder.append(", ");
                    }

                    queryBuilder.append(field.getName()).append(" = ?");
                    values.add(value);

                    atLeastOneFieldUpdated = true;
                }

                field.setAccessible(false);
            }

            if (!atLeastOneFieldUpdated) {
                return false;
            }

            queryBuilder.append(" WHERE ");

            int i = 0;
            for (Object primaryKeyField : _primaryKey) {
                Field field = _class.getDeclaredField(primaryKeyField.toString());
                field.setAccessible(true);

                queryBuilder.append(primaryKeyField).append(" = ?");
                values.add(field.get(_object));

                field.setAccessible(false);

                if (i < _primaryKey.size() - 1) {
                    queryBuilder.append(" AND ");
                }

                i++;
            }

            int rowsUpdated = _run.update(this.conn, queryBuilder.toString(), values.toArray());

            return rowsUpdated > 0;
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("Error updating the object.", e);
        }
    }


    /**
     * Deletes the record from the associated database table based on the constructed query.
     *
     * @return `true` if the record was successfully deleted, `false` otherwise.
     */
    @Override
    public boolean delete() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder queryBuilder = new StringBuilder("DELETE FROM ").append(_table).append(" WHERE ");

            int i = 0;
            for (Object primaryKeyField : _primaryKey) {
                Field field = _class.getField(primaryKeyField.toString());
                field.setAccessible(true);
                queryBuilder.append(primaryKeyField).append(" = ?");
                if (i++ < _primaryKey.size() - 1) {
                    queryBuilder.append(" AND ");
                }
                field.setAccessible(false);
            }

            int rowsDeleted = _run.update(this.conn, queryBuilder.toString(), _primaryKey.toArray());

            return rowsDeleted > 0;
        } catch (SQLException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deletes the record from the associated database table based on the constructed query.
     *
     * @return `true` if the record was successfully deleted, `false` otherwise.
     */
    @Override
    public boolean deleteByIDs(Object[] keys) {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder queryBuilder = new StringBuilder("DELETE FROM ").append(_table).append(" WHERE ");

            int i = 0;
            for (Object primaryKeyField : _primaryKey) {
                Field field = _class.getField(primaryKeyField.toString());
                field.setAccessible(true);
                queryBuilder.append(primaryKeyField).append(" = ?");
                if (i++ < _primaryKey.size() - 1) {
                    queryBuilder.append(" AND ");
                }
                field.setAccessible(false);
            }

            int rowsDeleted = _run.update(this.conn, queryBuilder.toString(), keys);

            return rowsDeleted > 0;
        } catch (SQLException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Performs a soft delete operation on the record based on the constructed query.
     *
     * @return `true` if the record was successfully softly deleted, `false` otherwise.
     */
    @Override
    public boolean softDelete() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            StringBuilder queryBuilder = new StringBuilder("UPDATE ").append(_table).append(" SET delete_at = NOW() WHERE ");

            int i = 0;
            for (Object primaryKeyField : _primaryKey) {
                Field field = _class.getField(primaryKeyField.toString());
                field.setAccessible(true);
                queryBuilder.append(primaryKeyField).append(" = ?");
                if (i++ < _primaryKey.size() - 1) {
                    queryBuilder.append(" AND ");
                }
                field.setAccessible(false);
            }

            int rowsDeleted = _run.update(this.conn, queryBuilder.toString(), _primaryKey.toArray());

            return rowsDeleted > 0;
        } catch (SQLException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes a raw SQL query.
     *
     * @param sql The SQL query to execute.
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    @Override
    public List<Map<String, Object>> query(final String sql) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
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
     * Executes a raw SQL query.
     *
     * @param sql    The SQL query to execute.
     * @param params The parameters to be used in the query.
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    @Override
    public List<Map<String, Object>> query(final String sql, final Object[] params) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = this.conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
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
     * Counts the number of records returned by the query.
     *
     * @return The number of records returned by the query.
     */
    @Override
    public int count() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                throw new SQLException("The connection to the database is closed or invalid.");
            }

            ResultSetHandler<Integer> q = new BeanHandler<>(Integer.class);

            return _run.query(this.conn, "SELECT COUNT(*) FROM " + _table, q);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes the database connection associated with this instance.
     */
    @Override
    public void close() {
        this.conn = database.closeConnection();
    }

    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(List.of(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

}