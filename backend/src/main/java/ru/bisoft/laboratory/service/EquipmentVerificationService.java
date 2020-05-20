package ru.bisoft.laboratory.service;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentVerification;

public interface EquipmentVerificationService extends GuideService<EquipmentVerification> {
	Page<EquipmentVerification> findByEquipment(Equipment sample, Pageable pageable);

	void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids);
}
