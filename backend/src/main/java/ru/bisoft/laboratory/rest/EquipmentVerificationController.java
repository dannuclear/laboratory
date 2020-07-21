package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentVerification;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.EquipmentVerificationService;

import javax.validation.Valid;

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
