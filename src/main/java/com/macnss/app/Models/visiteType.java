package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class visiteType {

	private String visite_type;

	private int taux_remboursement;

}
