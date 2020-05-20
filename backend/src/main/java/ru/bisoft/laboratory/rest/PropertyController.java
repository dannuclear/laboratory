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
import ru.bisoft.laboratory.domain.Property;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.PropertyService;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

	private final PropertyService propertyService;

	@GetMapping("/{property}")
	public ResponseEntity<Property> findOne(@PathVariable Property property) {
		return ResponseEntity.ok(property);
	}

	@GetMapping
	public ResponseEntity<PagedModel<Property>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
		Page<Property> page = propertyService.findByString(search, pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public Property save(@Valid @RequestBody final Property property) {
		return propertyService.save(property);
	}

	@PutMapping("/{property}")
	public Property save(@PathVariable Property property, @Valid @RequestBody Property newProperty) {
		propertyService.save(newProperty);
		return newProperty;
	}

	@DeleteMapping("/{property}")
	public void delete(@PathVariable Property property) {
		propertyService.delete(property);
	}
}
