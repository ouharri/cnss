package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class visite extends Document  {

	private java.lang.String medcin;

	private Date visite_date;

	private java.lang.String visit_reson;

}
