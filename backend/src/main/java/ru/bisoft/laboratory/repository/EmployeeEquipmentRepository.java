package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

@Transactional(readOnly = true)
public interface EmployeeEquipmentRepository extends JpaRepository<EmployeeEquipment, Integer> {
	@EntityGraph(attributePaths = { "employee" })
	Page<EmployeeEquipment> findByEquipment(Equipment equipment, Pageable p);

	@EntityGraph(attributePaths = { "equipment" })
	Page<EmployeeEquipment> findByEmployee(Employee employee, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEmployee(Employee employee);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipment(Equipment equipment);
}
