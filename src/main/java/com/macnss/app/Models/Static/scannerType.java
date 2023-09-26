package com.macnss.app.Models.Static;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class scannerType {

	private String scannerType;
	private int reimbursementRate;

}
