package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Department;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.DepartmentService;

import javax.validation.Valid;

@RestController
@RequestMapping("organizations/{organization}/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{department}")
    public ResponseEntity<Department> findOne(@PathVariable Department department) {
        return ResponseEntity.ok(department);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Department>> findByOrganization(@PathVariable Organization organization, Pageable pageable) {
        Page<Department> page = departmentService.findByOrganization(organization, pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody @Valid Department newDepartment) {
        newDepartment.setId(null);
        departmentService.save(newDepartment);
    }

    @PutMapping("/{department}")
    public void save(@PathVariable Department department, @RequestBody @Valid Department newDepartment) {
        departmentService.save(newDepartment);
    }

    @DeleteMapping("/{department}")
    public void delete(@PathVariable Department department) {
        departmentService.delete(department);
    }
}
