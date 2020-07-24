package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.DocumentEquipmentService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class DocumentEquipmentController {

    private final DocumentEquipmentService equipmentDocumentService;

    @GetMapping("documents/{document}/equipments/{equipmentDocument}")
    public ResponseEntity<DocumentEquipment> findOne(@PathVariable DocumentEquipment equipmentDocument) {
        return ResponseEntity.ok(equipmentDocument);
    }

    @RequestMapping("documents/{document}/equipments")
    public ResponseEntity<PagedModel<DocumentEquipment>> findByDocument(Document document, Pageable pageable) {
        Page<DocumentEquipment> page = equipmentDocumentService.findByDocument(document, pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @RequestMapping("equipments/{equipment}/documents")
    public ResponseEntity<PagedModel<DocumentEquipment>> findByEquipment(Equipment equipment, Pageable pageable) {
        Page<DocumentEquipment> page = equipmentDocumentService.findByEquipment(equipment, pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping("documents/{document}/equipments")
    public DocumentEquipment save(@Valid @RequestBody final DocumentEquipment equipmentDocument) {
        return equipmentDocumentService.save(equipmentDocument);
    }

    @PutMapping("documents/{document}/equipments/{equipmentDocument}")
    public DocumentEquipment save(@PathVariable DocumentEquipment equipmentDocument, @Valid @RequestBody DocumentEquipment newDocumentEquipment) {
        return equipmentDocumentService.save(newDocumentEquipment);
    }

    @DeleteMapping("documents/{document}/equipments/{equipmentDocument}")
    public void delete(@PathVariable DocumentEquipment equipmentDocument) {
        equipmentDocumentService.delete(equipmentDocument);
    }
}
