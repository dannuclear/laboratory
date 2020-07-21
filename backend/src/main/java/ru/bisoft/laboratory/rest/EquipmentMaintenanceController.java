package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentMaintenance;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.EquipmentMaintenanceService;

import javax.validation.Valid;

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
