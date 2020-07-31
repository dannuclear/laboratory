package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEmployee;

@Transactional(readOnly = true)
public interface SelectionEmployeeRepository extends JpaRepository<SelectionEmployee, Integer> {
	@EntityGraph(attributePaths = { "selection.request" })
	Page<SelectionEmployee> findByEmployee(Employee employee, Pageable p);

	@EntityGraph(attributePaths = { "employee.organization", "employee.post", "employee.department" })
	Page<SelectionEmployee> findBySelection(Selection selection, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteBySelection(Selection selection);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEmployee(Employee employee);
}
