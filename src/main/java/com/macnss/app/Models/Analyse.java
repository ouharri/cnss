package com.macnss.app.Models;

import com.macnss.app.Models.Abstract.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Analyse extends Document {

	private String laboratoire;

	private String adress_lab;

	private int analyse_date;

}
