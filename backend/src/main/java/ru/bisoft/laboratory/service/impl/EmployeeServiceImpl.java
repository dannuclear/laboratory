package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.*;
import ru.bisoft.laboratory.repository.EmployeeRepository;
import ru.bisoft.laboratory.service.EmployeeDocumentService;
import ru.bisoft.laboratory.service.EmployeeEquipmentService;
import ru.bisoft.laboratory.service.EmployeePropertyService;
import ru.bisoft.laboratory.service.EmployeeService;

import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE_ADMIN')")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDocumentService employeeDocumentService;
    private final EmployeePropertyService employeePropertyService;
    private final EmployeeEquipmentService employeeEquipmentService;

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
    public Employee create() {
        log.info("Создаем служащего");
        Employee employee = new Employee();
        return employee;
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_READ') or hasRole('EMPLOYEE_ADMIN')")
    public Employee findById(Integer id) {
        log.info("Извлекаем сотрудника по id {}", id);
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_READ') or hasRole('EMPLOYEE_ADMIN')")
    public Page<Employee> findAll(Pageable pageable) {
        log.info("Извлекаем все служащегоы постранично {}", pageable);
        System.out.println("Im TUT");
        return employeeRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
    public Employee save(Employee entity) {
        log.info("Сохраняем служащего {}", entity);
        Employee result = employeeRepository.save(entity);

        // Сохраняем документы по служащему
        if (entity.getEmployeeDocuments() != null) {
            employeeDocumentService.deleteByEmployeeAndIdNotIn(entity, entity.getEmployeeDocuments().stream().map(EmployeeDocument::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getEmployeeDocuments().forEach(ed -> ed.setEmployee(entity));
            employeeDocumentService.saveAll(entity.getEmployeeDocuments());
        }
        // Сохраняем свойства по служащему
        if (entity.getEmployeeProperties() != null) {
            employeePropertyService.deleteByEmployeeAndIdNotIn(entity, entity.getEmployeeProperties().stream().map(EmployeeProperty::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getEmployeeProperties().forEach(ed -> ed.setEmployee(entity));
            employeePropertyService.saveAll(entity.getEmployeeProperties());
        }
        // Сохраняем оборудование по служащему
        if (entity.getEmployeeEquipments() != null) {
            employeeEquipmentService.deleteByEmployeeAndIdNotIn(entity, entity.getEmployeeEquipments().stream().map(EmployeeEquipment::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getEmployeeEquipments().forEach(ed -> ed.setEmployee(entity));
            employeeEquipmentService.saveAll(entity.getEmployeeEquipments());
        }

        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
    public void delete(Employee entity) {
        log.info("Удаляем служащего {}", entity);
        employeeRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
    public Iterable<Employee> saveAll(Iterable<Employee> entities) {
        log.info("Сохраняем коллекцию служащегоов {}", entities);
        return employeeRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_READ') or hasRole('EMPLOYEE_ADMIN')")
    public Page<Employee> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все служащих по фильтру {} постранично {}", value, pageable);
        return employeeRepository.findByNameContainsIgnoreCase(value, pageable);
    }

    @Override
    public Page<Employee> findByOrganization(Organization organization, Pageable pageable) {
        log.info("Извлекаем всех служащих организации {} постранично {}", organization, pageable);
        Page<Employee> result = employeeRepository.findByOrganization(organization, pageable);
        result.getContent().forEach(sp -> sp.setOrganization(null));
        return result;
    }
}
