package ru.bisoft.laboratory.service.impl;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentVerification;
import ru.bisoft.laboratory.repository.EquipmentVerificationRepository;
import ru.bisoft.laboratory.service.EquipmentVerificationService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EQUIPMENT_VERIFICATION_ADMIN')")
public class EquipmentVerificationServiceImpl implements EquipmentVerificationService {

	private final EquipmentVerificationRepository equipmentVerificationRepository;

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_WRITE') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public EquipmentVerification create() {
		log.info("Создаем поверку оборудования");
		EquipmentVerification equipmentVerification = new EquipmentVerification();
		return equipmentVerification;
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_READ') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public EquipmentVerification findById(Integer id) {
		log.info("Извлекаем поверку оборудования по id {}", id);
		return equipmentVerificationRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_READ') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public Page<EquipmentVerification> findAll(Pageable pageable) {
		log.info("Извлекаем все поверки оборудования постранично {}", pageable);
		return equipmentVerificationRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_WRITE') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public EquipmentVerification save(EquipmentVerification entity) {
		log.info("Сохраняем поверку оборудования {}", entity);
		return equipmentVerificationRepository.save(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_WRITE') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public void delete(EquipmentVerification entity) {
		log.info("Удаляем поверку оборудования {}", entity);
		equipmentVerificationRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_READ') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public Iterable<EquipmentVerification> saveAll(Iterable<EquipmentVerification> entities) {
		log.info("Сохраняем коллекцию поверки оборудования {}", entities);
		return equipmentVerificationRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_READ') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public Page<EquipmentVerification> findByString(String value, Pageable pageable) {
		throw new UnsupportedOperationException("Неподдерживаемая опрация");
	}

	@Override
	public Page<EquipmentVerification> findByEquipment(Equipment equipment, Pageable pageable) {
		log.info("Извлекаем все поверки оборудования {} постранично {}", equipment, pageable);
		Page<EquipmentVerification> result = equipmentVerificationRepository.findByEquipment(equipment, pageable);
		result.getContent().forEach(sp -> sp.setEquipment(null));
		return result;
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_VERIFICATION_WRITE') or hasRole('EQUIPMENT_VERIFICATION_ADMIN')")
	public void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids) {
		if (ids == null)
			throw new IllegalArgumentException("ids не может быть пустым");
		log.info("Удаляем у оборудования {} поверки с id {}", equipment, ids);
		if (ids.size() == 0)
			equipmentVerificationRepository.deleteByEquipment(equipment);
		else
			equipmentVerificationRepository.deleteByEquipmentAndIdNotIn(equipment, ids);
	}
}
