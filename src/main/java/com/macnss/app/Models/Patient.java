package com.macnss.app.Models;

import com.macnss.app.Enums.FileStatus;

public class Patient extends User {

	private String matricule;

	public FileStatus viewStatut(String matricule) {
		return null;
	}

	public List<RefundFile> viewDossier(String matricule) {
		return null;
	}

	public List<Document> viewDocument(String matricule) {
		return null;
	}

}
