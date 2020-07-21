package ru.bisoft.laboratory.repository;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ControlTypeRepository {
}
