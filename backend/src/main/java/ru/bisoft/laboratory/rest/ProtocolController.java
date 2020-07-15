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
import ru.bisoft.laboratory.domain.Protocol;
import ru.bisoft.laboratory.domain.ProtocolSample;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.repository.ProtocolSampleRepository;
import ru.bisoft.laboratory.service.ProtocolService;

@RestController
@RequestMapping("protocols")
@RequiredArgsConstructor
public class ProtocolController {

	private final ProtocolService protocolService;
	private final ProtocolSampleRepository protocolSampleRepository;

	@GetMapping("/{protocol}")
	public ResponseEntity<Protocol> findOne(@PathVariable Protocol protocol) {
		return ResponseEntity.ok(protocol);
	}

	@GetMapping
	public ResponseEntity<PagedModel<Protocol>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
		Page<Protocol> page = protocolService.findAll(pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public void save(@RequestBody @Valid Protocol newProtocol) {
		newProtocol.setId(null);
		protocolService.save(newProtocol);
	}

	@PutMapping("/{protocol}")
	public void save(@PathVariable Protocol protocol, @RequestBody @Valid Protocol newProtocol) {
		protocolService.save(newProtocol);
	}

	@DeleteMapping("/{protocol}")
	public void delete(@PathVariable Protocol protocol) {
		protocolService.delete(protocol);
	}

	@GetMapping("/{protocol}/samples")
	public ResponseEntity<Iterable<ProtocolSample>> samples(Pageable pageable, @PathVariable Protocol protocol) {
		Page<ProtocolSample> result = protocolSampleRepository.findByProtocol(protocol, pageable);
		result.forEach(ps -> ps.setProtocol(null));
		return ResponseEntity.ok(PagedModel.wrap(result));
	}
}
