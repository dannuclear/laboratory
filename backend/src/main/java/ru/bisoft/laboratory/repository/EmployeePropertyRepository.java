package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeProperty;
import ru.bisoft.laboratory.domain.Property;

@Transactional(readOnly = true)
public interface EmployeePropertyRepository extends JpaRepository<EmployeeProperty, Integer> {
    @EntityGraph(attributePaths = {"employee"})
    Page<EmployeeProperty> findByProperty(Property property, Pageable p);

    @EntityGraph(attributePaths = {"property"})
    Page<EmployeeProperty> findByEmployee(Employee employee, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEmployee(Employee employee);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByPropertyAndIdNotIn(Property property, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByProperty(Property property);
}
