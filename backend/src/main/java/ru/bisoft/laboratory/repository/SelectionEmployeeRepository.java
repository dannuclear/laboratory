package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEmployee;
import ru.bisoft.laboratory.domain.equipment.Equipment;

@Transactional(readOnly = true)
public interface SelectionEmployeeRepository extends JpaRepository<SelectionEmployee, Integer> {
	@EntityGraph(attributePaths = { "selection" })
	Page<SelectionEmployee> findByEquipment(Equipment equipment, Pageable p);

	@EntityGraph(attributePaths = { "employee" })
	Page<SelectionEmployee> findBySelection(Selection selection, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteBySelection(Selection selection);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipment(Equipment equipment);
}
