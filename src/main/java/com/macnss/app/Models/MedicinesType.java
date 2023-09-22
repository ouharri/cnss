package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MedicinesType {

	private String code;

	private String name;

	private String dci1;

	private double dosage1;

	private String unite_dosage1;

	private int forme;

	private String presentation;

	private float ppv;

	private String ph;

	private int prix_br;

	private String princips_generique;

	private int taux_remboursement;

}
