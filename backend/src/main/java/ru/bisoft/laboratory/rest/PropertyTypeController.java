package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.PropertyType;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.PropertyTypeService;

import javax.validation.Valid;

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
