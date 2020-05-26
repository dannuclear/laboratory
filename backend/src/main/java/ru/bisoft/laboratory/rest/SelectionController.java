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
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.SelectionService;

@RestController
@RequestMapping("/selections")
@RequiredArgsConstructor
public class SelectionController {

	private final SelectionService selectionService;

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
}
