package com.macnss.app.Models.user;

import com.macnss.Libs.orm.PrimaryKey;
import com.macnss.Libs.orm.Table;

import java.sql.Date;
import java.util.UUID;

import com.macnss.Libs.orm.smiyaMoa9ata;
import lombok.Data;

/**
 * Represents an Employee, extending the 'employee' class.
 */
@Data
@PrimaryKey
@Table(name = "employees")
public class employees implements smiyaMoa9ata {

    private String cnie = null;
    private String first_name = null;
    private String last_name = null;
    private String email = null;
    private String phone = null;
    private Date birthday = null;
    private String pwd_hash = null;
    private String gender = null;
    public UUID id;

}