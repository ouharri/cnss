package com.macnss.app.Models.document;

import com.macnss.app.Models.Abstract.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class Analyse extends Document {

	private String laboratory;
	private String labAddress;
	private Date analysisDate;

}
