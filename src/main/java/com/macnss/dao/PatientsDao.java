package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Patient;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class PatientsDao extends Model {

    private Patient patient = new Patient();

    public PatientsDao() {
        super("patients", new String[]{"matriculate"});
    }

    public PatientsDao(Patient patient) {
        super("patients", new String[]{"matriculate"});
        this.patient = patient;
    }

    public Patient read() {
        Map<String, String> patientData = super.read(new String[]{patient.getMatriculate()});

        if (patientData != null) {
            patient.setUser(
                    patientData.get("cnie"),
                    patientData.get("first_name"),
                    patientData.get("last_name"),
                    Date.valueOf(patientData.get("birthday")),
                    Gender.valueOf(patientData.get("gender")),
                    patientData.get("email"),
                    patientData.get("phone"),
                    patientData.get("birthday")
            );

            patient.setMatriculate(patientData.get("matriculate"));

            return patient;
        } else {
            return null;
        }
    }

    public Optional<Patient> save() throws SQLException {
        if (super.create(patient.getPatient()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(read());
        }
    }

    public Optional<Patient> get(String matriculate) {
        Map<String, String> patientData = super.read("matriculate", matriculate);

        if (patientData == null) {
            return Optional.empty();
        }

        patient.setUser(
                patientData.get("cnie"),
                patientData.get("first_name"),
                patientData.get("last_name"),
                Date.valueOf(patientData.get("birthday")),
                Gender.valueOf(patientData.get("gender")),
                patientData.get("email"),
                patientData.get("phone"),
                patientData.get("birthday")
        );

        patient.setMatriculate(patientData.get("matriculate"));

        return Optional.of(patient);
    }

    public boolean delete(Patient patients) {
        return super.delete(new String[]{patients.getMatriculate()});
    }
}
