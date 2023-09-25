package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Administrator;
import com.macnss.interfaces.Dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdministratorDao extends Model implements Dao<Administrator> {

    Administrator administrator = new Administrator();

    public AdministratorDao() {
        super("administrators", new String[]{"administrator_id"});
    }

    @Override
    public Administrator read() {
        Map<String, String> Admin = super.read(new String[]{String.valueOf(administrator.getAdministrator_id())});

        if (Admin != null) {
            administrator.setUser(
                    Admin.get("cnie"),
                    Admin.get("first_name"),
                    Admin.get("last_name"),
                    Gender.valueOf(Admin.get("gender")),
                    Admin.get("email"),
                    Admin.get("phone"),
                    Admin.get("password")
            );

            administrator.setAdministrator_id(Integer.parseInt(Admin.get("administrator_id")));

            return administrator;
        } else {
            return null;
        }
    }

    @Override
    public Optional<Administrator> get(String id) {
        Map<String, String> Admin = super.read("email", id);

        if (Admin == null) return Optional.empty();

        administrator.setUser(
                Admin.get("cnie"),
                Admin.get("first_name"),
                Admin.get("last_name"),
                Gender.valueOf(Admin.get("gender")),
                Admin.get("email"),
                Admin.get("phone"),
                Admin.get("pwd_hash")
        );

        administrator.setAdministrator_id(Integer.parseInt(Admin.get("administrator_id")));

        return Optional.of(administrator);
    }

    public List<Administrator> getAll() {

        List<Administrator> Administrators = new ArrayList<>();

        List<Map<String, String>> Admins = super.retrieveAll();

        Admins.forEach((admin) -> {
            Administrator administrator = new Administrator();

            administrator.setUser(
                    admin.get("cnie"),
                    admin.get("first_name"),
                    admin.get("last_name"),
                    Gender.valueOf(admin.get("gender")),
                    admin.get("email"),
                    admin.get("phone"),
                    admin.get("password")
            );
            administrator.setAdministrator_id(Integer.parseInt(admin.get("administrator_id")));
            Administrators.add(administrator);
        });

        return Administrators;
    }

    @Override
    public Optional<Administrator> save() throws SQLException {
        if (super.create(administrator.getAdministrator()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(read());
        }
    }

    @Override
    public Optional<Administrator> create(Administrator entity) throws SQLException {
        if (super.create(entity.getAdministrator()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(read());
        }
    }

    @Override
    public Optional<Administrator> update(Administrator administrator) {
        if (super.update(administrator.getAdministrator(), new String[]{String.valueOf(administrator.getAdministrator_id())})) {
            return Optional.of(administrator);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Administrator> find(String criteria) {
        List<Administrator> Administrators = new ArrayList<>();

        List<Map<String, String>> Admins = super.readAll(new String[]{criteria});

        Admins.forEach((admin) -> {
            Administrator administrator = new Administrator();

            administrator.setUser(
                    admin.get("cnie"),
                    admin.get("first_name"),
                    admin.get("last_name"),
                    Gender.valueOf(admin.get("gender")),
                    admin.get("email"),
                    admin.get("phone"),
                    admin.get("password")
            );
            administrator.setAdministrator_id(Integer.parseInt(admin.get("administrator_id")));

            Administrators.add(administrator);
        });

        return Administrators;
    }

    @Override
    public boolean delete(Administrator administrator) {
        return super.delete(new String[]{String.valueOf(administrator.getAdministrator_id())});
    }

}
