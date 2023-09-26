package com.macnss.interfaces.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * An interface for Data Access Objects (DAOs) that perform CRUD operations on entities of type T.
 *
 * @param <T> The type of entity to be managed.
 */
public interface Dao<T> {

    /**
     * Reads an entity based on certain criteria.
     *
     * @return The read entity.
     */
    T read();

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id The unique identifier of the entity.
     * @return An optional containing the entity if found, or an empty optional if not found.
     */
    Optional<T> get(String id);

    /**
     * Retrieves all entities of type T.
     *
     * @return A list of all entities.
     */
    List<T> getAll();

    /**
     * Saves the entity to the database.
     *
     * @return An optional containing the saved entity, or an empty optional if there's an error.
     * @throws SQLException If an SQL error occurs during the save operation.
     */
    Optional<T> save() throws SQLException;

    /**
     * Creates a new entity in the database.
     *
     * @param entity The entity to be created.
     * @return An optional containing the created entity, or an empty optional if there's an error.
     */
    Optional<T> create(T entity) throws SQLException;

    /**
     * Updates an existing entity in the database.
     *
     * @param entity The entity to be updated.
     * @return An optional containing the updated entity, or an empty optional if there's an error.
     */
    Optional<T> update(T entity);

    /**
     * Finds all entities based on certain criteria.
     *
     * @param criteria The search criteria.
     * @return A list of entities that match the criteria.
     */
    List<T> find(String criteria);

    /**
     * Deletes an entity from the database.
     *
     * @param entity The entity to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
    boolean delete(T entity);
}