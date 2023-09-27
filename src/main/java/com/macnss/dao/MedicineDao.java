package com.macnss.dao;

import com.macnss.Libs.Model;
import com.macnss.app.Models.Static.Medicine;

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
        Map<String, Object> medicineData = super.read(new String[]{"code"}, new String[]{code});

        if (medicineData == null) {
            return Optional.empty();
        }

        Medicine medicine = new Medicine();

        medicine.setCode(code);
        medicine.setName( (String) medicineData.get("name"));
        medicine.setDc1( (String) medicineData.get("dc1"));
        medicine.setDosage((Double) medicineData.get("dosage"));
        medicine.setDosageUnit( (String) medicineData.get("dosage_unit"));
        medicine.setForm( (String) medicineData.get("form"));
        medicine.setPresentation( (String) medicineData.get("presentation"));
        medicine.setPpv(Double.parseDouble( (String) medicineData.get("ppv")));
        medicine.setPh( (String) medicineData.get("ph"));
        medicine.setGrossPrice(Double.parseDouble( (String) medicineData.get("gross_price")));
        medicine.setGenericPrinciples( (String) medicineData.get("generic_principles"));
        medicine.setReimbursementRate(Double.parseDouble( (String) medicineData.get("reimbursement_rate")));

        return Optional.of(medicine);
    }

    public List<Medicine> getAll() {
        List<Medicine> medicines = new ArrayList<>();

        List<Map<String, Object>> medicineDataList = super.retrieveAll();

        medicineDataList.forEach((medicineData) -> {
            Medicine medicine = new Medicine();

            medicine.setCode((String) medicineData.get("code"));
            medicine.setName((String) medicineData.get("name"));
            medicine.setDc1((String) medicineData.get("dc1"));
            medicine.setDosage(Double.parseDouble((String) medicineData.get("dosage")));
            medicine.setDosageUnit((String) medicineData.get("dosage_unit"));
            medicine.setForm((String) medicineData.get("form"));
            medicine.setPresentation((String) medicineData.get("presentation"));
            medicine.setPpv(Double.parseDouble((String) medicineData.get("ppv")));
            medicine.setPh((String) medicineData.get("ph"));
            medicine.setGrossPrice(Double.parseDouble((String) medicineData.get("gross_price")));
            medicine.setGenericPrinciples((String) medicineData.get("generic_principles"));
            medicine.setReimbursementRate(Double.parseDouble((String) medicineData.get("reimbursement_rate")));

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