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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.EquipmentService;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

	private final EquipmentService equipmentService;

	@GetMapping("/{equipment}")
	public ResponseEntity<Equipment> findOne(@PathVariable Equipment equipment) {
		return ResponseEntity.ok(equipment);
	}

	@GetMapping
	public ResponseEntity<PagedModel<Equipment>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
		Page<Equipment> page = equipmentService.findByString(search, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public void save(@RequestBody Equipment newEquipment) {
		newEquipment.setId(null);
		equipmentService.save(newEquipment);
	}

	@PutMapping("/{equipment}")
	public void save(@PathVariable Equipment equipment, @RequestBody @Valid Equipment newEquipment) {
		equipmentService.save(newEquipment);
	}

	@DeleteMapping("/{equipment}")
	public void delete(@PathVariable Equipment equipment) {
		equipmentService.delete(equipment);
	}
}
