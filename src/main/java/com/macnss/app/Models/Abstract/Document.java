package com.macnss.app.Models.Abstract;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class Document {

	private String code;
	private String description;
	private double payed_price;
	private String url;
}
