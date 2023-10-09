package com.macnss.app.Models.user;

import com.macnss.Libs.orm.PrimaryKey;
import com.macnss.Libs.orm.Table;

import java.util.UUID;

import com.macnss.Libs.orm.schema;
import com.macnss.app.Models.Abstract.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Represents an Employee, extending the 'employee' class.
 */

@Table(name = "employees")
@EqualsAndHashCode(callSuper = true)
public @Data class employees extends User implements schema {

    public @PrimaryKey UUID id;

}