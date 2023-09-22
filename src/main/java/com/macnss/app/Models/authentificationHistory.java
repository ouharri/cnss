package com.macnss.app.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = false)
public class authentificationHistory {

	private Timestamp authentificated_at;

}
