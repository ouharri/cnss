package com.macnss.app.Models;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class employee extends User {

    private Timestamp last_authentification = null;

    private Timestamp email_verified_at = null;

    private String auth_code = null;

    private Timestamp auth_code_generated_at = null;

}
