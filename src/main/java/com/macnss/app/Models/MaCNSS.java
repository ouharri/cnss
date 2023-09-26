package com.macnss.app.Models;

import com.macnss.app.Models.user.AgentCNSS;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MaCNSS {

	private List<AgentCNSS> agents;

	private List<com.macnss.app.Models.user.Administrator> Administrator;

}
