package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Administrator;
import com.macnss.interfaces.Dao.Dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing Administrator entities.
 */
public class AdministratorDao extends Model implements Dao<Administrator> {

    private final Administrator administrator;

    /**
     * Constructs a new AdministratorDao with a specified Administrator entity.
     *
     * @param admin The Administrator entity to work with.
     */
    public AdministratorDao(Administrator admin) {
        super("administrators", new String[]{"administrator_id"});
        this.administrator = admin;
    }

    /**
     * Reads an Administrator entity based on its unique identifier.
     *
     * @return The read Administrator entity or null if not found.
     */
    @Override
    public Administrator read() {
        Map<String, Object> adminData = super.read(new String[]{String.valueOf(administrator.getAdministrator_id())});

        if (adminData != null) {
            administrator.setUser(
                    (String) adminData.get("cnie"),
                    (String) adminData.get("first_name"),
                    (String) adminData.get("last_name"),
                    (Date) adminData.get("birthday"),
                    Gender.valueOf((String) adminData.get("gender")),
                    (String) adminData.get("email"),
                    (String) adminData.get("phone"),
                    (String) adminData.get("password")
            );

            administrator.setAdministrator_id(Integer.parseInt((String) adminData.get("administrator_id")));

            return administrator;
        } else {
            return null;
        }
    }

    /**
     * Saves the Administrator entity to the database.
     *
     * @return An optional containing the saved Administrator entity, or an empty optional if there's an error.
     * @throws SQLException If an SQL error occurs during the save operation.
     */
    @Override
    public Optional<Administrator> save() throws SQLException {
        if (super.create(administrator.getAdministrator()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(read());
        }
    }

    /**
     * Retrieves an Administrator entity by its email.
     *
     * @param email The email of the Administrator to retrieve.
     * @return An optional containing the Administrator if found, or an empty optional if not found.
     */
    @Override
    public Optional<Administrator> get(String email) {
        Map<String, Object> adminData = super.read(new String[]{"email"}, new String[]{email});

        if (adminData == null) {
            return Optional.empty();
        }

        administrator.setUser(
                (String) adminData.get("cnie"),
                (String) adminData.get("first_name"),
                (String) adminData.get("last_name"),
                (Date) adminData.get("birthday"),
                Gender.valueOf((String) adminData.get("gender")),
                (String) adminData.get("email"),
                (String) adminData.get("phone"),
                (String) adminData.get("pwd_hash")
        );

        administrator.setAdministrator_id((Integer) adminData.get("administrator_id"));

        return Optional.of(administrator);
    }

    /**
     * Retrieves all Administrator entities.
     *
     * @return A list of all Administrator entities.
     */
    public List<Administrator> getAll() {

        List<Administrator> Administrators = new ArrayList<>();

        List<Map<String, Object>> Admins = super.retrieveAll();

        Admins.forEach((adminData) -> {
            Administrator administrator = new Administrator();

            administrator.setUser(
                    (String) adminData.get("cnie"),
                    (String) adminData.get("first_name"),
                    (String) adminData.get("last_name"),
                    (Date) adminData.get("birthday"),
                    Gender.valueOf((String) adminData.get("gender")),
                    (String) adminData.get("email"),
                    (String) adminData.get("phone"),
                    (String) adminData.get("password")
            );
            administrator.setAdministrator_id(Integer.parseInt((String) adminData.get("administrator_id")));
            Administrators.add(administrator);
        });

        return Administrators;
    }

    /**
     * Creates a new Administrator entity in the database.
     *
     * @param entity The Administrator entity to be created.
     * @return An optional containing the created Administrator entity, or an empty optional if there's an error.
     * @throws SQLException if a database error occurs during creation.
     */
    @Override
    public Optional<Administrator> create(Administrator entity) throws SQLException {
        if (super.create(entity.getAdministrator()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    /**
     * Updates an existing Administrator entity in the database.
     *
     * @param administrator The Administrator entity to be updated.
     * @return An optional containing the updated Administrator entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<Administrator> update(Administrator administrator) {
        if (super.update(administrator.getAdministrator(), new String[]{String.valueOf(administrator.getAdministrator_id())})) {
            return Optional.of(administrator);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Finds all Administrator entities based on a search criteria.
     *
     * @param criteria The search criteria.
     * @return A list of Administrator entities that match the criteria.
     */
    @Override
    public List<Administrator> find(String criteria) {
        List<Administrator> Administrators = new ArrayList<>();

        List<Map<String, Object>> Admins = super.readAll(new String[]{criteria});

        Admins.forEach((adminData) -> {
            Administrator administrator = new Administrator();

            administrator.setUser(
                    (String) adminData.get("cnie"),
                    (String) adminData.get("first_name"),
                    (String) adminData.get("last_name"),
                    (Date) adminData.get("birthday"),
                    Gender.valueOf((String) adminData.get("gender")),
                    (String) adminData.get("email"),
                    (String) adminData.get("phone"),
                    (String) adminData.get("password")
            );
            administrator.setAdministrator_id((Integer) adminData.get("administrator_id"));

            Administrators.add(administrator);
        });

        return Administrators;
    }

    /**
     * Deletes an Administrator entity from the database.
     *
     * @param administrator The Administrator entity to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
    @Override
    public boolean delete(Administrator administrator) {
        return super.delete(new Object[]{administrator.getAdministrator_id()});
    }
}