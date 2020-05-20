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
import ru.bisoft.laboratory.domain.equipment.EquipmentVerification;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.EquipmentVerificationService;

@RestController
@RequestMapping("equipments/{equipment}/verifications")
@RequiredArgsConstructor
public class EquipmentVerificationController {

	private final EquipmentVerificationService equipmentVerificationService;

	@GetMapping("/{equipmentVerification}")
	public ResponseEntity<EquipmentVerification> findOne(@PathVariable EquipmentVerification equipmentVerification) {
		return ResponseEntity.ok(equipmentVerification);
	}

	@GetMapping
	public ResponseEntity<PagedModel<EquipmentVerification>> findByEquipment(Equipment equipment, Pageable pageable) {
		Page<EquipmentVerification> page = equipmentVerificationService.findByEquipment(equipment, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public EquipmentVerification save(@Valid @RequestBody final EquipmentVerification equipmentVerification) {
		return equipmentVerificationService.save(equipmentVerification);
	}

	@PutMapping("/{equipmentVerification}")
	public EquipmentVerification save(@PathVariable EquipmentVerification equipmentVerification, @Valid @RequestBody EquipmentVerification newEquipmentVerification) {
		return equipmentVerificationService.save(newEquipmentVerification);
	}

	@DeleteMapping("/{equipmentVerification}")
	public void delete(@PathVariable EquipmentVerification equipmentVerification) {
		equipmentVerificationService.delete(equipmentVerification);
	}
}
