package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.Organization;

public interface EmployeeService extends GuideService<Employee> {

	Page<Employee> findByOrganization(Organization organization, Pageable pageable);
}
