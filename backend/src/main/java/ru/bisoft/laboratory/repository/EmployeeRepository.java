package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.Organization;

@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Page<Employee> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

	@EntityGraph(attributePaths = { "post", "department", "organization" })
	Page<Employee> findByOrganization(Organization organization, Pageable p);
}
