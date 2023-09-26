package com.macnss.app.Models.Static;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class VisitType {

	private int visitTypeId;
	private String visitType;
	private double reimbursementRate;

	public Map<String,String> getVisitTypes() {
		return Map.of(
				"visitTypeId", String.valueOf(this.visitTypeId),
				"visitType", this.visitType,
				"reimbursementRate", String.valueOf(this.reimbursementRate)
		);
	}
}
