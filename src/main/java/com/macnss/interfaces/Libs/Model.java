package com.macnss.interfaces.Libs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The `Model` interface defines the standard CRUD (Create, Read, Update, Delete) operations
 * that can be performed on a database table.
 */
public interface Model {

    /**
     * Retrieves all records from the associated database table.
     *
     * @return A list of maps, where each map represents a row of data with column names as keys.
     */
    List<Map<String, Object>> retrieveAll();

    /**
     * Creates a new record in the associated database table with the provided data.
     *
     * @param data A map of column names and their corresponding values for the new record.
     * @return The primary key value (e.g., ID) of the newly created record, or null if the operation fails.
     * @throws SQLException If a database access error occurs.
     */
    Object create(Map<String, Object> data) throws SQLException;

    /**
     * Reads a single record from the associated database table based on the specified primary key values.
     *
     * @param ids An array of primary key values used to identify the record.
     * @return A map representing the retrieved record's data, or null if the record is not found.
     */
    Map<String, Object> read(Object[] ids);

    /**
     * Reads a single record from the associated database table based on specific column value(s).
     *
     * @param columnNames The name of the column(s) to search.
     * @param values      The value(s) to search for in the specified column(s).
     * @return A map representing the retrieved record's data, or null if the record is not found.
     */
    Map<String, Object> read(String[] columnNames, Object[] values);

    /**
     * Updates records in the associated database table with the provided data based on specified primary key values.
     *
     * @param data A map of column names and their corresponding values for the update.
     * @param ids  An array of primary key values used to identify the records to be updated.
     * @return True if the update operation is successful; otherwise, false.
     */
    boolean update(Map<String, Object> data, Object[] ids);

    /**
     * Deletes records from the associated database table based on specified primary key values.
     *
     * @param ids An array of primary key values used to identify the records to be deleted.
     * @return True if the delete operation is successful; otherwise, false.
     */
    boolean delete(Object[] ids);
}
