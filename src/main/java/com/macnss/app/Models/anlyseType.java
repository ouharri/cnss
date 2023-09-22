package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class anlyseType {

	private String analyse_type;

	private int taux_remboursement;

}
