package com.macnss.app.Models;

import com.macnss.app.Enums.FileStatus;
import com.macnss.app.Models.Abstract.Document;
import com.macnss.app.Models.Abstract.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {

    private String matriculate;

    private List<RefundFile> refundFileList;

    private List<Document> documentList;


    public Map<String, String> getPatient() {
        Map<String, String> patient = new HashMap<>();

        patient.put("administrator_id", String.valueOf(this.matriculate));
        patient.put("cnie", this.cnie);
        patient.put("first_name", this.first_name);
        patient.put("last_name", this.last_name);
        patient.put("email", this.email);
        patient.put("phone", this.phone);
        patient.put("gender", this.gender.toString());
        patient.put("password", this.password);

        return patient;
    }

}
