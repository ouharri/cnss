package com.macnss.app.Models;

import com.macnss.app.Enums.FileStatus;
import com.macnss.app.Models.Abstract.Document;
import com.macnss.app.Models.Abstract.User;

public class Patient extends User {

    private String matriculate;

    public List<RefundFile> viewDossier(String matriculate) {
        return null;
    }

    public List<Document> viewDocument(String matriculate) {
        return null;
    }

}
