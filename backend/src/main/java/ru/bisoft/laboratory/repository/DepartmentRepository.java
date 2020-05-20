package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Department;
import ru.bisoft.laboratory.domain.Organization;

@Transactional(readOnly = true)
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
	Page<Department> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

	Page<Department> findByOrganization(Organization organization, Pageable p);
}
