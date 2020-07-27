package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.OrganizationService;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @GetMapping("/{organization}")
    public ResponseEntity<Organization> findOne(@PathVariable Organization organization) {
        return ResponseEntity.ok(organization);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Organization>> findAll(Pageable pageable) {
        Page<Organization> page = organizationService.findAll(pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody Organization newOrganization) {
        newOrganization.setId(null);
        organizationService.save(newOrganization);
    }

    @PutMapping("/{organization}")
    public void save(@PathVariable Organization organization, @RequestBody Organization newOrganization) {
        organizationService.save(newOrganization);
    }

    @DeleteMapping("/{organization}")
    public void delete(@PathVariable Organization organization) {
        organizationService.delete(organization);
    }
}
