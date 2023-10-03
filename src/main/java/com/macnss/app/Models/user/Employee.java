package com.macnss.app.Models.user;

import com.macnss.app.Models.Abstract.User;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends User {

    private int employee_id;

}
