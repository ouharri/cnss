package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.user.Patient;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public class PatientsDao extends Model {

    private final Patient patient;

    public PatientsDao(Patient patient) {
        super("patients", new String[]{"matriculate"});
        this.patient = patient;
    }

    public Patient read() {
        Map<String, Object> patientData = super.read(new String[]{patient.getMatriculate()});

        if (patientData != null) {
            patient.setUser(
                    (String) patientData.get("cnie"),
                    (String) patientData.get("first_name"),
                    (String) patientData.get("last_name"),
                    Date.valueOf( (String) patientData.get("birthday")),
                    Gender.valueOf( (String) patientData.get("gender")),
                    (String) patientData.get("email"),
                    (String) patientData.get("phone"),
                    (String) patientData.get("birthday")
            );

            patient.setMatriculate((String) patientData.get("matriculate"));

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
        Map<String, Object> patientData = super.read(new String[]{"matriculate"}, new String[]{matriculate});

        if (patientData == null) {
            return Optional.empty();
        }

        patient.setUser(
                (String) patientData.get("cnie"),
                (String) patientData.get("first_name"),
                (String) patientData.get("last_name"),
                Date.valueOf( (String) patientData.get("birthday")),
                Gender.valueOf( (String) patientData.get("gender")),
                (String) patientData.get("email"),
                (String) patientData.get("phone"),
                (String) patientData.get("birthday")
        );

        patient.setMatriculate( (String) patientData.get("matriculate"));

        return Optional.of(patient);
    }

    public boolean delete(Patient patients) {
        return super.delete(new String[]{patients.getMatriculate()});
    }
}
