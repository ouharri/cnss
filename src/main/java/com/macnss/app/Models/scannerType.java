package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class scannerType {

	private String scanner_type;

	private int taux_remboursement;

}
