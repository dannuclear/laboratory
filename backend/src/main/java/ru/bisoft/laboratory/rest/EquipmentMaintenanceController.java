package ru.bisoft.laboratory.rest;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentMaintenance;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.EquipmentMaintenanceService;

@RestController
@RequestMapping("equipments/{equipment}/maintenances")
@RequiredArgsConstructor
public class EquipmentMaintenanceController {

	private final EquipmentMaintenanceService equipmentMaintenanceService;

	@GetMapping("/{equipmentMaintenance}")
	public ResponseEntity<EquipmentMaintenance> findOne(@PathVariable EquipmentMaintenance equipmentMaintenance) {
		return ResponseEntity.ok(equipmentMaintenance);
	}

	@GetMapping
	public ResponseEntity<PagedModel<EquipmentMaintenance>> findByEquipment(Equipment equipment, Pageable pageable) {
		Page<EquipmentMaintenance> page = equipmentMaintenanceService.findByEquipment(equipment, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public EquipmentMaintenance save(@Valid @RequestBody final EquipmentMaintenance equipmentMaintenance) {
		return equipmentMaintenanceService.save(equipmentMaintenance);
	}

	@PutMapping("/{equipmentMaintenance}")
	public EquipmentMaintenance save(@PathVariable EquipmentMaintenance equipmentMaintenance, @Valid @RequestBody EquipmentMaintenance newEquipmentMaintenance) {
		return equipmentMaintenanceService.save(newEquipmentMaintenance);
	}

	@DeleteMapping("/{equipmentMaintenance}")
	public void delete(@PathVariable EquipmentMaintenance equipmentMaintenance) {
		equipmentMaintenanceService.delete(equipmentMaintenance);
	}
}
