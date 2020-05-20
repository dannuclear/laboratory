package ru.bisoft.laboratory.domain;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Session {
	private String username;
	private Date expAt;
}
