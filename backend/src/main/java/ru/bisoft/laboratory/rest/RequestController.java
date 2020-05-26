package ru.bisoft.laboratory.rest;

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
import ru.bisoft.laboratory.domain.Request;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.RequestService;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

	private final RequestService requestService;

	@GetMapping("/{request}")
	public ResponseEntity<Request> findOne(@PathVariable Request request) {
		return ResponseEntity.ok(request);
	}

	@GetMapping
	public ResponseEntity<PagedModel<Request>> findAll(Pageable pageable) {
		Page<Request> page = requestService.findAll(pageable);
		return ResponseEntity.ok(PagedModel.wrap(page));
	}

	@PostMapping
	public void save(@RequestBody Request newRequest) {
		newRequest.setId(null);
		requestService.save(newRequest);
	}

	@PutMapping("/{request}")
	public void save(@PathVariable Request request, @RequestBody Request newRequest) {
		requestService.save(newRequest);
	}

	@DeleteMapping("/{request}")
	public void delete(@PathVariable Request request) {
		requestService.delete(request);
	}
}
