package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Models.FileMedicine;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing FileMedicine entities.
 */
public class FileMedicineDao extends Model {

    public FileMedicineDao() {
        super("files_medicines", new String[]{"medicine_id"});
    }

    /**
     * Creates a new FileMedicine entity in the database.
     *
     * @param entity The FileMedicine entity to be created.
     * @return An optional containing the created FileMedicine entity, or an empty optional if there's an error.
     * @throws SQLException if a database error occurs during creation.
     */
    public Optional<FileMedicine> create(FileMedicine entity) throws SQLException {
        if (super.create(entity.getFileMedicine()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    // Ajoutez ici d'autres méthodes spécifiques si nécessaire
}