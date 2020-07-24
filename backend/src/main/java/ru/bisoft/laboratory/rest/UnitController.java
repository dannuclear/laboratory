package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Unit;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.UnitService;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping("/{unit}")
    public ResponseEntity<Unit> findOne(@PathVariable Unit unit) {
        return ResponseEntity.ok(unit);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Unit>> findAll(Pageable pageable) {
        Page<Unit> page = unitService.findAll(pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody Unit newUnit) {
        newUnit.setId(null);
        unitService.save(newUnit);
    }

    @PutMapping("/{unit}")
    public void save(@PathVariable Unit unit, @RequestBody Unit newUnit) {
        unitService.save(newUnit);
    }

    @DeleteMapping("/{unit}")
    public void delete(@PathVariable Unit unit) {
        unitService.delete(unit);
    }
}
