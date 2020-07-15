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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.SamplePropertyService;

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
		page.getContent().forEach(sp->sp.setSample(null));
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
