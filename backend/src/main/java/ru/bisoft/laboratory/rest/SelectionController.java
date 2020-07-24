package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionSample;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.repository.SelectionSampleRepository;
import ru.bisoft.laboratory.service.SelectionService;

@RestController
@RequestMapping("/selections")
@RequiredArgsConstructor
public class SelectionController {

    private final SelectionService selectionService;
    private final SelectionSampleRepository selectionSampleRepository;

    @GetMapping("/{selection}")
    public ResponseEntity<Selection> findOne(@PathVariable Selection selection) {
        return ResponseEntity.ok(selection);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Selection>> findAll(Pageable pageable) {
        Page<Selection> page = selectionService.findAll(pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody Selection newSelection) {
        newSelection.setId(null);
        selectionService.save(newSelection);
    }

    @PutMapping("/{selection}")
    public void save(@PathVariable Selection selection, @RequestBody Selection newSelection) {
        selectionService.save(newSelection);
    }

    @DeleteMapping("/{selection}")
    public void delete(@PathVariable Selection selection) {
        selectionService.delete(selection);
    }

    @GetMapping("/{selection}/samples")
    public ResponseEntity<Iterable<SelectionSample>> samples(Pageable pageable, @PathVariable Selection selection) {
        Page<SelectionSample> result = selectionSampleRepository.findBySelection(selection, pageable);
        result.forEach(ps -> ps.setSelection(null));
        return ResponseEntity.ok(PagedModel.wrap(result));
    }
}
