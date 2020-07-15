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
import ru.bisoft.laboratory.domain.ObjectClass;
import ru.bisoft.laboratory.domain.ObjectClassDocument;
import ru.bisoft.laboratory.domain.ObjectClassEquipment;
import ru.bisoft.laboratory.domain.ObjectClassProperty;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.repository.ObjectClassDocumentRepository;
import ru.bisoft.laboratory.repository.ObjectClassEquipmentRepository;
import ru.bisoft.laboratory.repository.ObjectClassPropertyRepository;
import ru.bisoft.laboratory.service.ObjectClassService;

@RestController
@RequestMapping("/objectClasses")
@RequiredArgsConstructor
public class ObjectClassController {

	private final ObjectClassService objectClassService;
	private final ObjectClassDocumentRepository objectClassDocumentRepository;
	private final ObjectClassPropertyRepository objectClassPropertyRepository;
	private final ObjectClassEquipmentRepository objectClassEquipmentRepository;

	@GetMapping("/{objectClass}")
	public ResponseEntity<ObjectClass> findOne(@PathVariable ObjectClass objectClass) {
		return ResponseEntity.ok(objectClass);
	}

	@GetMapping
	public ResponseEntity<PagedModel<ObjectClass>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
		Page<ObjectClass> page = objectClassService.findByString(search, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public ObjectClass save(@Valid @RequestBody final ObjectClass objectClass) {
		return objectClassService.save(objectClass);
	}

	@PutMapping("/{objectClass}")
	public ObjectClass save(@PathVariable ObjectClass objectClass, @Valid @RequestBody ObjectClass newObjectClass) {
		objectClassService.save(newObjectClass);
		return newObjectClass;
	}

	@GetMapping("/{objectClass}/documents")
	public ResponseEntity<Iterable<ObjectClassDocument>> documents(Pageable pageable, @PathVariable ObjectClass objectClass) {
		Page<ObjectClassDocument> result = objectClassDocumentRepository.findByObjectClass(objectClass, pageable);
		result.forEach(ocp -> ocp.setObjectClass(null));
		return ResponseEntity.ok(PagedModel.wrap(result));
	}

	@GetMapping("/{objectClass}/properties")
	public ResponseEntity<Iterable<ObjectClassProperty>> properties(Pageable pageable, @PathVariable ObjectClass objectClass) {
		Page<ObjectClassProperty> result = objectClassPropertyRepository.findByObjectClass(objectClass, pageable);
		result.forEach(ocp -> ocp.setObjectClass(null));
		return ResponseEntity.ok(PagedModel.wrap(result));
	}

	@GetMapping("/{objectClass}/equipments")
	public ResponseEntity<Iterable<ObjectClassEquipment>> equipments(Pageable pageable, @PathVariable ObjectClass objectClass) {
		Page<ObjectClassEquipment> result = objectClassEquipmentRepository.findByObjectClass(objectClass, pageable);
		result.forEach(ocp -> ocp.setObjectClass(null));
		return ResponseEntity.ok(PagedModel.wrap(result));
	}

	@DeleteMapping("/{objectClass}")
	public void delete(@PathVariable ObjectClass objectClass) {
		objectClassService.delete(objectClass);
	}
}
