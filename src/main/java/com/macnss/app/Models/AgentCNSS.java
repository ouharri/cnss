package com.macnss.app.Models;

import com.macnss.app.Models.Abstract.employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AgentCNSS extends employee {

    String agent_cns_id;

}
