package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Department;
import ru.bisoft.laboratory.domain.Organization;

public interface DepartmentService extends GuideService<Department> {

    Page<Department> findByOrganization(Organization organization, Pageable pageable);
}
