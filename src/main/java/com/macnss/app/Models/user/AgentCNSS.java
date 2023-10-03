package com.macnss.app.Models.user;

import com.macnss.app.Enums.AgentStatus;
import com.macnss.app.Enums.Gender;
import com.macnss.app.Models.Abstract.employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class AgentCNSS extends employee {

    int agent_cns_id = 0;
    AgentStatus status = null;

    public Map<String, Object> getAgentCNSS() {
        Map<String, Object> agents = super.getUser();
        if(this.agent_cns_id != 0) agents.put("agent_id", this.agent_cns_id);
        if(this.status != null) agents.put("status", this.status);
        return agents;
    }

}