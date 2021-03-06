package ru.bisoft.laboratory.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.dto.PagedModel;
import ru.bisoft.laboratory.service.EmployeeService;

import javax.validation.Valid;

@RestController
@RequestMapping("organizations/{organization}/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{employee}")
    public ResponseEntity<Employee> findOne(@PathVariable Employee employee) {
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Employee>> findByOrganization(@PathVariable Organization organization, Pageable pageable) {
        Page<Employee> page = employeeService.findByOrganization(organization, pageable);
        return ResponseEntity.ok(PagedModel.wrap(page));
    }

    @PostMapping
    public void save(@RequestBody @Valid Employee newEmployee) {
        newEmployee.setId(null);
        employeeService.save(newEmployee);
    }

    @PutMapping("/{employee}")
    public void save(@PathVariable Employee employee, @RequestBody @Valid Employee newEmployee) {
        employeeService.save(newEmployee);
    }

    @DeleteMapping("/{employee}")
    public void delete(@PathVariable Employee employee) {
        employeeService.delete(employee);
    }
}
