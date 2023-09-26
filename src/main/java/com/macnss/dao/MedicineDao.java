package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Models.Medicine;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MedicineDao extends Model {
    public MedicineDao() {
        super("medicines", new String[]{"code"});
    }

    public Optional<Medicine> get(String code) {
        Map<String, String> medicineData = super.read("code", code);

        if (medicineData == null) {
            return Optional.empty();
        }

        Medicine medicine = new Medicine();

        medicine.setCode(code);
        medicine.setName(medicineData.get("name"));
        medicine.setDc1(medicineData.get("dc1"));
        medicine.setDosage(Double.parseDouble(medicineData.get("dosage")));
        medicine.setDosageUnit(medicineData.get("dosage_unit"));
        medicine.setForm(medicineData.get("form"));
        medicine.setPresentation(medicineData.get("presentation"));
        medicine.setPpv(Double.parseDouble(medicineData.get("ppv")));
        medicine.setPh(medicineData.get("ph"));
        medicine.setGrossPrice(Double.parseDouble(medicineData.get("gross_price")));
        medicine.setGenericPrinciples(medicineData.get("generic_principles"));
        medicine.setReimbursementRate(Double.parseDouble(medicineData.get("reimbursement_rate")));

        return Optional.of(medicine);
    }

    public List<Medicine> getAll() {
        List<Medicine> medicines = new ArrayList<>();

        List<Map<String, String>> medicineDataList = super.retrieveAll();

        medicineDataList.forEach((medicineData) -> {
            Medicine medicine = new Medicine();

            medicine.setCode(medicineData.get("code"));
            medicine.setName(medicineData.get("name"));
            medicine.setDc1(medicineData.get("dc1"));
            medicine.setDosage(Double.parseDouble(medicineData.get("dosage")));
            medicine.setDosageUnit(medicineData.get("dosage_unit"));
            medicine.setForm(medicineData.get("form"));
            medicine.setPresentation(medicineData.get("presentation"));
            medicine.setPpv(Double.parseDouble(medicineData.get("ppv")));
            medicine.setPh(medicineData.get("ph"));
            medicine.setGrossPrice(Double.parseDouble(medicineData.get("gross_price")));
            medicine.setGenericPrinciples(medicineData.get("generic_principles"));
            medicine.setReimbursementRate(Double.parseDouble(medicineData.get("reimbursement_rate")));

            medicines.add(medicine);
        });

        return medicines;
    }

    public Optional<Medicine> create(Medicine entity) throws SQLException {
        if (super.create(entity.getMedicine()) == null) {
            return Optional.empty();
        } else {
            return Optional.of(entity);
        }
    }

    public Optional<Medicine> update(Medicine medicine) {
        if (super.update(medicine.getMedicine(), new String[]{medicine.getCode()})) {
            return Optional.of(medicine);
        } else {
            return Optional.empty();
        }
    }

    public boolean delete(Medicine medicine) {
        return super.delete(new String[]{medicine.getCode()});
    }
}