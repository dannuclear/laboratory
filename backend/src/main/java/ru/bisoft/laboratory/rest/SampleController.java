package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.SampleService;

import javax.validation.Valid;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/{sample}")
    public ResponseEntity<Sample> findOne(@PathVariable Sample sample) {
        return ResponseEntity.ok(sample);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Sample>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
        Page<Sample> page = sampleService.findByString(search, pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public Sample save(@Valid @RequestBody final Sample sample) {
        return sampleService.save(sample);
    }

    @PutMapping("/{sample}")
    public Sample save(@PathVariable Sample sample, @Valid @RequestBody Sample newSample) {
        return sampleService.save(newSample);
    }

    @DeleteMapping("/{sample}")
    public void delete(@PathVariable Sample sample) {
        sampleService.delete(sample);
    }
}
