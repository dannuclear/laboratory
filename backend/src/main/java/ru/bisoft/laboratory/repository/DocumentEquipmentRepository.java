package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

@Transactional(readOnly = true)
public interface DocumentEquipmentRepository extends JpaRepository<DocumentEquipment, Integer> {
	@EntityGraph(attributePaths = { "document" })
	Page<DocumentEquipment> findByEquipment(Equipment equipment, Pageable p);

	@EntityGraph(attributePaths = { "equipment" })
	Page<DocumentEquipment> findByDocument(Document document, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids);
	
	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipment(Equipment equipment);
}
