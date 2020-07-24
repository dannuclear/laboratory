package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.SampleDepartment;
import ru.bisoft.laboratory.domain.SampleDepartmentEmployee;

@Transactional(readOnly = true)
public interface SampleDepartmentEmployeeRepository extends JpaRepository<SampleDepartmentEmployee, Integer> {
    @EntityGraph(attributePaths = {"sampleDepartment"})
    Page<SampleDepartmentEmployee> findByEmployee(Employee employee, Pageable p);

    @EntityGraph(attributePaths = {"employee"})
    Page<SampleDepartmentEmployee> findBySampleDepartment(SampleDepartment sampleDepartment, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySampleDepartmentAndIdNotIn(SampleDepartment sampleDepartment, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySampleSampleDepartment(SampleDepartment sampleDepartment);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEmployee(Employee employee);
}
