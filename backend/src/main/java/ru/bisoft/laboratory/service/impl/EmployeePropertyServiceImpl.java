package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeProperty;
import ru.bisoft.laboratory.domain.Property;
import ru.bisoft.laboratory.repository.EmployeePropertyRepository;
import ru.bisoft.laboratory.service.EmployeePropertyService;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE_PROPERTY_ADMIN')")
public class EmployeePropertyServiceImpl implements EmployeePropertyService {

    private final EmployeePropertyRepository employeePropertyRepository;

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_WRITE') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public EmployeeProperty create() {
        log.info("Создаем документ оборудования");
        EmployeeProperty employeeProperty = new EmployeeProperty();
        return employeeProperty;
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_READ') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public EmployeeProperty findById(Integer id) {
        log.info("Извлекаем отдел по id {}", id);
        return employeePropertyRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_READ') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public Page<EmployeeProperty> findAll(Pageable pageable) {
        log.info("Извлекаем все документы оборудования постранично {}", pageable);
        return employeePropertyRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_WRITE') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public EmployeeProperty save(EmployeeProperty entity) {
        log.info("Сохраняем документ оборудования {}", entity);
        return employeePropertyRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_WRITE') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public void delete(EmployeeProperty entity) {
        log.info("Удаляем документ оборудования {}", entity);
        employeePropertyRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_WRITE') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public void deleteByEmployeeAndIdNotIn(Employee employee, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", employee, ids);
        if (ids.size() == 0)
            employeePropertyRepository.deleteByEmployee(employee);
        else
            employeePropertyRepository.deleteByEmployeeAndIdNotIn(employee, ids);
    }

    @Override
    public void deleteByPropertyAndIdNotIn(Property property, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", property, ids);
        if (ids.size() == 0)
            employeePropertyRepository.deleteByProperty(property);
        else
            employeePropertyRepository.deleteByPropertyAndIdNotIn(property, ids);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_READ') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public Iterable<EmployeeProperty> saveAll(Iterable<EmployeeProperty> entities) {
        log.info("Сохраняем коллекцию документов оборудования {}", entities);
        return employeePropertyRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_PROPERTY_READ') or hasRole('EMPLOYEE_PROPERTY_ADMIN')")
    public Page<EmployeeProperty> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<EmployeeProperty> findByProperty(Property property, Pageable pageable) {
        log.info("Извлекаем всех сотрудников по оборудованию {} постранично {}", property, pageable);
        Page<EmployeeProperty> result = employeePropertyRepository.findByProperty(property, pageable);
        result.getContent().forEach(de -> {
            de.setProperty(null);
            de.getEmployee().setEmployeeProperties(null);
        });
        return result;
    }

    @Override
    public Page<EmployeeProperty> findByEmployee(Employee employee, Pageable pageable) {
        log.info("Извлекаем все документы оборудования по документу {} постранично {}", employee, pageable);
        Page<EmployeeProperty> result = employeePropertyRepository.findByEmployee(employee, pageable);
        result.getContent().forEach(de -> {
            de.setEmployee(null);
            de.getProperty().setEmployeeProperties(null);
        });
        return result;
    }
}
