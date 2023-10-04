package com.macnss.database.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Models.Static.VisitType;
import com.macnss.interfaces.Dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VisitTypesDao extends Model implements Dao<VisitType> {

    private final VisitType visitTypes;

    public VisitTypesDao(VisitType visitTypes) {
        super("visit_types", new String[]{"visit_type_id"});
        this.visitTypes = visitTypes;
    }

    @Override
    public VisitType read() {
        Map<String, Object> visitTypeData = super.read(new Object[]{visitTypes.getVisitTypeId()});

        if (visitTypeData != null) {
            visitTypes.setVisitTypeId((Integer) visitTypeData.get("visit_type_id"));
            visitTypes.setVisitType( (String) visitTypeData.get("visit_type"));
            visitTypes.setReimbursementRate(Double.parseDouble( (String) visitTypeData.get("visit_reimbursement_rate")));

            return visitTypes;
        } else {
            return null;
        }
    }

    @Override
    public Optional<VisitType> get(String id) {
        return Optional.empty();
    }

    @Override
    public List<VisitType> getAll() {
        return null;
    }

    @Override
    public Optional<VisitType> save() throws SQLException {
        if (super.create(visitTypes.getVisitTypes()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(read());
        }
    }

    @Override
    public Optional<VisitType> create(VisitType entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<VisitType> update(VisitType entity) {
        return Optional.empty();
    }

    /**
     * Updates an entity based on certain criteria.
     *
     * @return An optional containing the updated entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<VisitType> update() {
        return Optional.empty();
    }

    /**
     * Finds all entities based on certain criteria.
     *
     * @param criteria The search criteria.
     * @return A list of entities that match the criteria.
     */
    @Override
    public List<VisitType> find(Object criteria) {
        return null;
    }

    public List<VisitType> find(String criteria) {
        return null;
    }

    @Override
    public boolean delete(VisitType visitTypes) {
        return super.delete(new String[]{String.valueOf(visitTypes.getVisitTypeId())});
    }

    /**
     * Deletes an entity based on certain criteria.
     *
     * @return True if the deletion is successful, otherwise false.
     */
    @Override
    public boolean delete() {
        return false;
    }
}
