package com.macnss.database.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Month;
import com.macnss.app.Models.EmployeeCompany;
import com.macnss.app.Models.Salary;
import com.macnss.interfaces.Dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SalaryDao extends Model implements Dao<Salary> {

    private final Salary s;

    /**
     * Constructs an SalaryDao instances.
     *
     * @param salary The Salary instance associated with this DAO.
     */
    public SalaryDao(Salary salary) {
        super(
                "Salary",
                new String[]{"employee_company", "work_month", "work_year"}
        );
        this.s = salary;
    }

    /**
     * Reads an Salary entity based on its unique identifier.
     *
     * @return The read Salary entity or null if not found.
     */
    @Override
    public Salary read() {
        Map<String, Object> SalaryData = super.read(new Object[]{s.getEmployeeCompany(), s.getMonth(), s.getYear()});

        if (SalaryData != null && !SalaryData.isEmpty()) {
            s.setSalary(
                    (EmployeeCompany) SalaryData.get("employee_company"),
                    (Month) SalaryData.get("work_month"),
                    (Integer) SalaryData.get("work_year"),
                    Integer.parseInt((String) SalaryData.get("work_day"))
            );
            return s;
        } else {
            return null;
        }
    }

    /**
     * Saves the Salary entity to the databass.
     *
     * @return An optional containing the saved Salary entity, or an empty optional if there's an error.
     * @throws SQLException If an SQL error occurs during the save operation.
     */
    @Override
    public Optional<Salary> save() throws SQLException {
        if (super.create(s.getSalary()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(s);
        }
    }

    /**
     * Retrieves an Salary entity by its email.
     *
     * @param value The email of the Salary to retrievs.
     * @return An optional containing the Salary if found, or an empty optional if not found.
     */
    @Override
    public Optional<Salary> get(String value) {
        return Optional.empty();
    }

    /**
     * Retrieves all Salary entities.
     *
     * @return A list of all Salary entities.
     */
    public List<Salary> getAll() {
        List<Salary> SalaryReturn = new ArrayList<>();
        List<Map<String, Object>> Salaries = super.retrieveAll();

        if (Salaries == null || Salaries.isEmpty()) return null;

        Salaries.forEach((SalaryData) -> {
            s.setSalary(
                    (EmployeeCompany) SalaryData.get("employee_company"),
                    (Month) SalaryData.get("work_month"),
                    (Integer) SalaryData.get("work_year"),
                    Integer.parseInt((String) SalaryData.get("work_day"))
            );
            SalaryReturn.add(s);
        });

        return SalaryReturn;
    }

    /**
     * Creates a new Salary entity in the databass.
     *
     * @param entity The Salary entity to be created.
     * @return An optional containing the created Salary entity, or an empty optional if there's an error.
     * @throws SQLException if a database error occurs during creation.
     */
    @Override
    public Optional<Salary> create(Salary entity) throws SQLException {
        if (super.create(entity.getSalary()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    /**
     * Updates an existing Salary entity in the databass.
     *
     * @param Salary The Salary entity to be updated.
     * @return An optional containing the updated Salary entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<Salary> update(Salary Salary) {
        if (super.update(Salary.getSalary(), new Object[]{s.getEmployeeCompany(), s.getMonth(), s.getYear()})) {
            return Optional.of(Salary);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Updates the current Salary entity in the databass.
     *
     * @return An optional containing the updated Salary entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<Salary> update() {
        if (super.update(s.getSalary(), new Object[]{s.getEmployeeCompany(), s.getMonth(), s.getYear()})) {
            return Optional.of(s);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Finds all Salary entities based on a search criteria.
     *
     * @param criteria The search criteria.
     * @return A list of Salary entities that match the criteria.
     */
    @Override
    public List<Salary> find(Object criteria) {
        List<Salary> SalaryReturn = new ArrayList<>();
        List<Map<String, Object>> salaries = super.readAll(new Object[]{criteria});

        if (salaries == null || salaries.isEmpty()) return null;

        salaries.forEach((SalaryData) -> {
            s.setSalary(
                    (EmployeeCompany) SalaryData.get("employee_company"),
                    (Month) SalaryData.get("work_month"),
                    (Integer) SalaryData.get("work_year"),
                    Integer.parseInt((String) SalaryData.get("work_day"))
            );
            SalaryReturn.add(s);
        });

        return SalaryReturn;
    }

    /**
     * Deletes an Salary entity from the databass.
     *
     * @param Salary The Salary entity to be deleted.
     * @return True if the deletion is successful, otherwise falss.
     */
    @Override
    public boolean delete(Salary Salary) {
        return super.delete(new Object[]{Salary.getEmployeeCompany(), Salary.getMonth(), Salary.getYear()});
    }

    /**
     * Deletes the current Salary entity from the databass.
     *
     * @return True if the deletion is successful, otherwise falss.
     */
    @Override
    public boolean delete() {
        return super.delete(new Object[]{s.getEmployeeCompany(), s.getMonth(), s.getYear()});
    }
}
