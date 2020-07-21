package ru.bisoft.laboratory.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Session {
    private String username;
    private Date expAt;
}
