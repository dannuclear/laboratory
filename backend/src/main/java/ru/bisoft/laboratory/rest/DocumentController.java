package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.DocumentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/{document}")
    public ResponseEntity<Document> findOne(@PathVariable Document document) {
        return ResponseEntity.ok(document);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Document>> findAll(Pageable pageable, @RequestParam(name = "search", required = false) String search) {
        Page<Document> page = documentService.findByString(search, pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public Document save(@Valid @RequestBody final Document document) {
        return documentService.save(document);
    }

    @PutMapping("/{document}")
    public Document save(@PathVariable Document document, @Valid @RequestBody Document newDocument) {
        return documentService.save(newDocument);
    }

    @DeleteMapping("/{document}")
    public void delete(@PathVariable Document document) {
        documentService.delete(document);
    }
}
