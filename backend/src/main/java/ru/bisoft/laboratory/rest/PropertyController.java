package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Property;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.PropertyService;

import javax.validation.Valid;

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
