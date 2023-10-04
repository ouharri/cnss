package com.macnss.database.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Employee;
import com.macnss.interfaces.Dao.Dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data Access Object (DAO) for managing Employee entities.
 */
public class EmployeeDao extends Model implements Dao<Employee> {

    private final Employee e;

    /**
     * Constructs an EmployeeDao instance.
     *
     * @param employee The Employee instance associated with this DAO.
     */
    public EmployeeDao(Employee employee) {
        super(
                "employees",
                new String[]{"Employee_id"}
        );
        this.e = employee;
    }

    /**
     * Reads an Employee entity based on its unique identifier.
     *
     * @return The read Employee entity or null if not found.
     */
    @Override
    public Employee read() {
        Map<String, Object> employeeData = super.read(new Object[]{e.getEmployee_id()});

        if (employeeData != null && !employeeData.isEmpty()) {
            e.setEmployee(
                    employeeData.get("cnie").toString(),
                    employeeData.get("first_name").toString(),
                    employeeData.get("last_name").toString(),
                    Date.valueOf(employeeData.get("birthday").toString()),
                    Gender.valueOf(employeeData.get("gender").toString()),
                    employeeData.get("email").toString(),
                    employeeData.get("phone").toString(),
                    employeeData.get("psw_hash").toString(),
                    Integer.parseInt(employeeData.get("Employee_id").toString())
            );
            return e;
        } else {
            return null;
        }
    }

    /**
     * Saves the Employee entity to the database.
     *
     * @return An optional containing the saved Employee entity, or an empty optional if there's an error.
     * @throws SQLException If an SQL error occurs during the save operation.
     */
    @Override
    public Optional<Employee> save() throws SQLException {
        if (super.create(e.getEmployee()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(e);
        }
    }

    /**
     * Retrieves an Employee entity by its email.
     *
     * @param email The email of the Employee to retrieve.
     * @return An optional containing the Employee if found, or an empty optional if not found.
     */
    @Override
    public Optional<Employee> get(String email) {
        Map<String, Object> employeeData = super.read(new String[]{"email"}, new Object[]{email});

        if (employeeData == null || employeeData.isEmpty()) return Optional.empty();

        e.setEmployee(
                employeeData.get("cnie").toString(),
                employeeData.get("first_name").toString(),
                employeeData.get("last_name").toString(),
                Date.valueOf(employeeData.get("birthday").toString()),
                Gender.valueOf(employeeData.get("gender").toString()),
                employeeData.get("email").toString(),
                employeeData.get("phone").toString(),
                employeeData.get("psw_hash").toString(),
                Integer.parseInt(employeeData.get("Employee_id").toString())
        );
        return Optional.of(e);
    }

    /**
     * Retrieves all Employee entities.
     *
     * @return A list of all Employee entities.
     */
    public List<Employee> getAll() {
        List<Employee> employeesReturn = new ArrayList<>();
        List<Map<String, Object>> employees = super.retrieveAll();

        if (employees == null || employees.isEmpty()) return null;

        employees.forEach((employeeData) -> {
            e.setEmployee(
                    employeeData.get("cnie").toString(),
                    employeeData.get("first_name").toString(),
                    employeeData.get("last_name").toString(),
                    Date.valueOf(employeeData.get("birthday").toString()),
                    Gender.valueOf(employeeData.get("gender").toString()),
                    employeeData.get("email").toString(),
                    employeeData.get("phone").toString(),
                    employeeData.get("psw_hash").toString(),
                    Integer.parseInt(employeeData.get("Employee_id").toString())
            );
            employeesReturn.add(e);
        });

        return employeesReturn;
    }

    /**
     * Creates a new Employee entity in the database.
     *
     * @param entity The Employee entity to be created.
     * @return An optional containing the created Employee entity, or an empty optional if there's an error.
     * @throws SQLException if a database error occurs during creation.
     */
    @Override
    public Optional<Employee> create(Employee entity) throws SQLException {
        if (super.create(entity.getEmployee()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    /**
     * Updates an existing Employee entity in the database.
     *
     * @param employee The Employee entity to be updated.
     * @return An optional containing the updated Employee entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<Employee> update(Employee employee) {
        if (super.update(employee.getEmployee(), new Object[]{employee.getEmployee_id()})) {
            return Optional.of(employee);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Updates the current Employee entity in the database.
     *
     * @return An optional containing the updated Employee entity, or an empty optional if there's an error.
     */
    @Override
    public Optional<Employee> update() {
        if (super.update(e.getEmployee(), new Object[]{e.getEmployee_id()})) {
            return Optional.of(e);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Finds all Employee entities based on a search criteria.
     *
     * @param criteria The search criteria.
     * @return A list of Employee entities that match the criteria.
     */
    @Override
    public List<Employee> find(Object criteria) {
        List<Employee> EmployeesReturn = new ArrayList<>();
        List<Map<String, Object>> employees = super.readAll(new Object[]{criteria});

        if (employees == null || employees.isEmpty()) return null;

        employees.forEach((employeeData) -> {
            e.setEmployee(
                    employeeData.get("cnie").toString(),
                    employeeData.get("first_name").toString(),
                    employeeData.get("last_name").toString(),
                    Date.valueOf(employeeData.get("birthday").toString()),
                    Gender.valueOf(employeeData.get("gender").toString()),
                    employeeData.get("email").toString(),
                    employeeData.get("phone").toString(),
                    employeeData.get("psw_hash").toString(),
                    Integer.parseInt(employeeData.get("agent_id").toString())
            );
            EmployeesReturn.add(e);
        });

        return EmployeesReturn;
    }

    /**
     * Deletes an Employee entity from the database.
     *
     * @param Employee The Employee entity to be deleted.
     * @return True if the deletion is successful, otherwise false.
     */
    @Override
    public boolean delete(Employee Employee) {
        return super.delete(new String[]{String.valueOf(Employee.getEmployee_id())});
    }

    /**
     * Deletes the current Employee entity from the database.
     *
     * @return True if the deletion is successful, otherwise false.
     */
    @Override
    public boolean delete() {
        return super.delete(new String[]{String.valueOf(e.getEmployee_id())});
    }
}
