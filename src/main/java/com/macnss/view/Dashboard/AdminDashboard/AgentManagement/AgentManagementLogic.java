package com.macnss.view.Dashboard.AdminDashboard.AgentManagement;

import com.macnss.dao.AgentDao;

import java.util.Date;

public class AgentManagementLogic {
    private AgentDao agentDao;
    public void create(String cnie, String firstName, String lastName, String email, String phone, Date birthday, String pwdHash, String gender) {
        agentDao = new AgentDao();
        agentDao.create(cnie, firstName, lastName, email, phone, birthday, pwdHash, gender);
    }
}
