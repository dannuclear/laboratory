package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.ControlType;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.ControlTypeService;

@RestController
@RequestMapping("/controlTypes")
@RequiredArgsConstructor
public class ControlTypeController {

    private final ControlTypeService controlTypeService;

    @GetMapping("/{controlType}")
    public ResponseEntity<ControlType> findOne(@PathVariable ControlType controlType) {
        return ResponseEntity.ok(controlType);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ControlType>> findAll(Pageable pageable) {
        Page<ControlType> page = controlTypeService.findAll(pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody ControlType newControlType) {
        newControlType.setId(null);
        controlTypeService.save(newControlType);
    }

    @PutMapping("/{controlType}")
    public void save(@PathVariable ControlType controlType, @RequestBody ControlType newControlType) {
        controlTypeService.save(newControlType);
    }

    @DeleteMapping("/{controlType}")
    public void delete(@PathVariable ControlType controlType) {
        controlTypeService.delete(controlType);
    }
}
