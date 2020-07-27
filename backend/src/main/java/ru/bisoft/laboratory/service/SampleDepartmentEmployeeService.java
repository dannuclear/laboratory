package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.SampleDepartmentEmployee;

public interface SampleDepartmentEmployeeService extends GuideService<SampleDepartmentEmployee> {

    Page<SampleDepartmentEmployee> findByEmployee(Employee employee, Pageable pageable);

    void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids);

    void deleteByEmployee(Employee employee);
}
