package com.macnss.app.Models;

import com.macnss.app.Models.Abstract.employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class AgentCNSS extends employee {

    int agent_cns_id;

    public Map<String, String> getAgentCNSS() {
        Map<String, String> agents = new HashMap<>();

        agents.put("administrator_id", String.valueOf(this.agent_cns_id));
        agents.put("cnie", this.cnie);
        agents.put("first_name", this.first_name);
        agents.put("last_name", this.last_name);
        agents.put("email", this.email);
        agents.put("phone", this.phone);
        agents.put("gender", this.gender.toString());
        agents.put("password", this.password);

        return agents;
    }

}
