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
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.SampleService;

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
