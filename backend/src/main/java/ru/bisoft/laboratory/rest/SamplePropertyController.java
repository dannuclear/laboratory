package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.SamplePropertyService;

import javax.validation.Valid;

@RestController
@RequestMapping("samples/{sample}/properties")
@RequiredArgsConstructor
public class SamplePropertyController {

    private final SamplePropertyService samplePropertyService;

    @GetMapping("/{sampleProperty}")
    public ResponseEntity<SampleProperty> findOne(@PathVariable SampleProperty sampleProperty) {
        return ResponseEntity.ok(sampleProperty);
    }

    @GetMapping
    public ResponseEntity<PagedModel<SampleProperty>> findBySample(Sample sample, Pageable pageable) {
        Page<SampleProperty> page = samplePropertyService.findBySample(sample, pageable);
        page.getContent().forEach(sp -> sp.setSample(null));
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public SampleProperty save(@Valid @RequestBody final SampleProperty sampleProperty) {
        return samplePropertyService.save(sampleProperty);
    }

    @PutMapping("/{sampleProperty}")
    public SampleProperty save(@PathVariable SampleProperty sampleProperty, @Valid @RequestBody SampleProperty newSampleProperty) {
        return samplePropertyService.save(newSampleProperty);
    }

    @DeleteMapping("/{sampleProperty}")
    public void delete(@PathVariable SampleProperty sampleProperty) {
        samplePropertyService.delete(sampleProperty);
    }
}
