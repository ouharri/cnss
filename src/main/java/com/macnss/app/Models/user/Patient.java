package com.macnss.app.Models.user;

import com.macnss.app.Models.Abstract.Document;
import com.macnss.app.Models.Abstract.User;
import com.macnss.app.Models.Refund.RefundFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {

    private String matriculate;

    private List<RefundFile> refundFileList;

    private List<Document> documentList;


    public Map<String, Object> getPatient() {
        Map<String, Object> patient = new HashMap<>();

        patient.put("administrator_id", this.matriculate);
        patient.put("cnie", this.cnie);
        patient.put("first_name", this.firstName);
        patient.put("last_name", this.lastName);
        patient.put("email", this.email);
        patient.put("phone", this.phone);
        patient.put("gender", this.gender);
        patient.put("password", this.password);

        return patient;
    }

}
