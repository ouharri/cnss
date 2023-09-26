package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PatientDao extends Model {
    public PatientDao() {
        super("patients", new String[]{"matriculate"});
    }

    public Optional<Patient> get(String matriculate) {
        Map<String, String> patientData = super.read("matriculate", matriculate);

        if (patientData == null) {
            return Optional.empty();
        }

        Patient patient = new Patient();

        patient.setUser(
                patientData.get("cnie"),
                patientData.get("first_name"),
                patientData.get("last_name"),
                Gender.valueOf(patientData.get("gender")),
                patientData.get("email"),
                patientData.get("phone"),
                patientData.get("pwd_hash")
        );

        patient.setMatriculate(matriculate);

        return Optional.of(patient);
    }

    public List<Patient> getAll() {
        List<Patient> patients = new ArrayList<>();

        List<Map<String, String>> patientDataList = super.retrieveAll();

        patientDataList.forEach((patientData) -> {
            Patient patient = new Patient();

            patient.setUser(
                    patientData.get("cnie"),
                    patientData.get("first_name"),
                    patientData.get("last_name"),
                    Gender.valueOf(patientData.get("gender")),
                    patientData.get("email"),
                    patientData.get("phone"),
                    patientData.get("pwd_hash")
            );

            patient.setMatriculate(patientData.get("matriculate"));

            patients.add(patient);
        });

        return patients;
    }

    public Optional<Patient> create(Patient entity) throws SQLException {
        if (super.create(entity.getPatient()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    public Optional<Patient> update(Patient patient) {
        if (super.update(patient.getPatient(), new String[]{patient.getMatriculate()})) {
            return Optional.of(patient);
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Patient patient) {
        return super.delete(new String[]{patient.getMatriculate()});
    }
}
