package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class scanner extends Document  {

	private String medcine;

	private Date scanner_date;

	private int scanner_resultat;

}
