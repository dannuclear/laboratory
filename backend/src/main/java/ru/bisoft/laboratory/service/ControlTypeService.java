package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.ControlType;

public interface ControlTypeService extends GuideService<ControlType> {

    Page<ControlType> findByAbbreviation(String value, Pageable pageable);
}
