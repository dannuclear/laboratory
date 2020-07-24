package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeProperty;
import ru.bisoft.laboratory.domain.Property;

import java.util.Collection;

public interface EmployeePropertyService extends GuideService<EmployeeProperty> {
    Page<EmployeeProperty> findByEmployee(Employee employee, Pageable pageable);

    Page<EmployeeProperty> findByProperty(Property property, Pageable pageable);

    void deleteByPropertyAndIdNotIn(Property property, Collection<Integer> ids);

    void deleteByEmployeeAndIdNotIn(Employee employee, Collection<Integer> ids);
}
