package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class visite extends Document  {

	private String medcin;

	private Date visite_date;

	private String visit_reson;

}
