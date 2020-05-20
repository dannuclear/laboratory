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
import ru.bisoft.laboratory.domain.PropertyType;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.PropertyTypeService;

@RestController
@RequestMapping("/propertyTypes")
@RequiredArgsConstructor
public class PropertyTypeController {

	private final PropertyTypeService propertyTypeService;

	@GetMapping("/{propertyType}")
	public ResponseEntity<PropertyType> findOne(@PathVariable PropertyType propertyType) {
		return ResponseEntity.ok(propertyType);
	}

	@GetMapping
	public ResponseEntity<PagedModel<PropertyType>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
		Page<PropertyType> page = propertyTypeService.findByString(search, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public PropertyType save(@Valid @RequestBody final PropertyType propertyType) {
		return propertyTypeService.save(propertyType);
	}

	@PutMapping("/{propertyType}")
	public PropertyType save(@PathVariable PropertyType propertyType, @Valid @RequestBody PropertyType newPropertyType) {
		propertyTypeService.save(newPropertyType);
		return newPropertyType;
	}

	@DeleteMapping("/{propertyType}")
	public void delete(@PathVariable PropertyType propertyType) {
		propertyTypeService.delete(propertyType);
	}
}
