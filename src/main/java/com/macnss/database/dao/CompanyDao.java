package com.macnss.database.dao;

import com.macnss.app.Models.company;
import com.macnss.Libs.orm.Model;

public class CompanyDao extends Model<company> {

    public CompanyDao() {
        _primaryKey.add("company_id");
    }

}