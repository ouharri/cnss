package com.macnss.app.Models.Static;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AnalyseType {

    private String analyseType;
    private int reimbursementRate; // refund rate

}
