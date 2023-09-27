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
        Map<String, Object> agents = new HashMap<>();

        if(this.agent_cns_id != 0) agents.put("agent_id", this.agent_cns_id);
        if(this.cnie != null) agents.put("cnie", this.cnie);
        if(this.firstName != null) agents.put("first_name", this.firstName);
        if(this.lastName != null) agents.put("last_name", this.lastName);
        if(this.email != null) agents.put("email", this.email);
        if(this.birthday != null) agents.put("birthday", this.birthday);
        if(this.phone != null) agents.put("phone", this.phone);
        if (this.gender != null) agents.put("gender",this.gender);
        if(this.password != null) agents.put("pwd_hash", this.password);
        if(this.status != null) agents.put("status", this.status);

        return agents;
    }

}