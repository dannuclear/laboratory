package ru.bisoft.laboratory.rest;

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
import ru.bisoft.laboratory.domain.LegalEntity;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.LegalEntityService;

@RestController
@RequestMapping("/legalEntities")
@RequiredArgsConstructor
public class LegalEntityController {

	private final LegalEntityService legalEntityService;

	@GetMapping("/{legalEntity}")
	public ResponseEntity<LegalEntity> findOne(@PathVariable LegalEntity legalEntity) {
		return ResponseEntity.ok(legalEntity);
	}

	@GetMapping
	public ResponseEntity<PagedModel<LegalEntity>> findAll(Pageable pageable) {
		Page<LegalEntity> page = legalEntityService.findAll(pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public void save(@RequestBody LegalEntity newLegalEntity) {
		newLegalEntity.setId(null);
		legalEntityService.save(newLegalEntity);
	}

	@PutMapping("/{legalEntity}")
	public void save(@PathVariable LegalEntity legalEntity, @RequestBody LegalEntity newLegalEntity) {
		legalEntityService.save(newLegalEntity);
	}

	@DeleteMapping("/{legalEntity}")
	public void delete(@PathVariable LegalEntity legalEntity) {
		legalEntityService.delete(legalEntity);
	}
}
