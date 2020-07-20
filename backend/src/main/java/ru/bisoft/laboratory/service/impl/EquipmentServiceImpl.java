package ru.bisoft.laboratory.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentMaintenance;
import ru.bisoft.laboratory.domain.equipment.EquipmentVerification;
import ru.bisoft.laboratory.repository.EquipmentRepository;
import ru.bisoft.laboratory.service.DocumentEquipmentService;
import ru.bisoft.laboratory.service.EquipmentMaintenanceService;
import ru.bisoft.laboratory.service.EquipmentService;
import ru.bisoft.laboratory.service.EquipmentVerificationService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EQUIPMENT_ADMIN')")
public class EquipmentServiceImpl implements EquipmentService {

	private final EquipmentRepository equipmentRepository;
	private final DocumentEquipmentService documentEquipmentService;
	private final EquipmentMaintenanceService equipmentMaintenanceService;
	private final EquipmentVerificationService equipmentVerificationService;

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_WRITE') or hasRole('EQUIPMENT_ADMIN')")
	public Equipment create() {
		log.info("Создаем оборудование");
		Equipment equipment = new Equipment();
		return equipment;
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_READ') or hasRole('EQUIPMENT_ADMIN')")
	public Equipment findById(Integer id) {
		log.info("Извлекаем оборудование по id {}", id);
		return equipmentRepository.findById(id).orElse(null);
	}
	
	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_READ') or hasRole('EQUIPMENT_ADMIN')")
	public Page<Equipment> findAll(Pageable pageable) {
		log.info("Извлекаем все оборудование постранично {}", pageable);
		return equipmentRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_WRITE') or hasRole('EQUIPMENT_ADMIN')")
	public Equipment save(Equipment entity) {
		log.info("Сохраняем оборудование {}", entity);
		Equipment result = equipmentRepository.save(entity);
		// Сохраняем документы по оборудованию
		if (entity.getDocumentEquipments() != null) {
			documentEquipmentService.deleteByEquipmentAndIdNotIn(entity, entity.getDocumentEquipments().stream().map(DocumentEquipment::getId).collect(Collectors.toList()));
			entity.getDocumentEquipments().forEach(de -> de.setEquipment(entity));
			documentEquipmentService.saveAll(entity.getDocumentEquipments());
		}
		// Сохраняем технические обслуживания по оборудованию
		if (entity.getMaintenanceList() != null) {
			equipmentMaintenanceService.deleteByEquipmentAndIdNotIn(entity, entity.getMaintenanceList().stream().map(EquipmentMaintenance::getId).collect(Collectors.toList()));
			entity.getMaintenanceList().forEach(de -> de.setEquipment(entity));
			equipmentMaintenanceService.saveAll(entity.getMaintenanceList());
		}
		// Сохраняем поверки по оборудованию
		if (entity.getVerificationList() != null) {
			equipmentVerificationService.deleteByEquipmentAndIdNotIn(entity, entity.getVerificationList().stream().map(EquipmentVerification::getId).collect(Collectors.toList()));
			entity.getVerificationList().forEach(de -> de.setEquipment(entity));
			equipmentVerificationService.saveAll(entity.getVerificationList());
		}
		equipmentRepository.updateMaintenanceAndVerification(entity);
		return result;
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_WRITE') or hasRole('EQUIPMENT_ADMIN')")
	public void delete(Equipment entity) {
		log.info("Удаляем оборудование {}", entity);
		equipmentRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_WRITE') or hasRole('EQUIPMENT_ADMIN')")
	public Iterable<Equipment> saveAll(Iterable<Equipment> entities) {
		log.info("Сохраняем коллекцию оборудование {}", entities);
		return equipmentRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('EQUIPMENT_READ') or hasRole('EQUIPMENT_ADMIN')")
	public Page<Equipment> findByString(String value, Pageable pageable) {
		if (StringUtils.isEmpty(value))
			return findAll(pageable);
		log.info("Извлекаем все оборудование по фильтру [{}] постранично [{}]", value, pageable);
		return equipmentRepository.findByNameContainsIgnoreCase(value, pageable);
	}
}
