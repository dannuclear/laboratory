package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentMaintenance;

@Transactional(readOnly = true)
public interface EquipmentMaintenanceRepository extends JpaRepository<EquipmentMaintenance, Integer> {
	Page<EquipmentMaintenance> findByEquipment(Equipment equipment, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	@Query(value = "DELETE FROM EquipmentMaintenance em where em.equipment = ?1 and em.id not in (?2)")
	void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByEquipment(Equipment equipment);
}
