package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

@Transactional(readOnly = true)
public interface SelectionEquipmentRepository extends JpaRepository<SelectionEquipment, Integer> {
	@EntityGraph(attributePaths = { "selection" })
	Page<SelectionEquipment> findByEquipment(Equipment equipment, Pageable p);

	@EntityGraph(attributePaths = { "equipment" })
	Page<SelectionEquipment> findBySelection(Selection selection, Pageable p);

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
