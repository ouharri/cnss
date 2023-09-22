package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MaCNSS {

	private List<AgentCNSS> agents;

	private List<Administrator> Administrator;

}
