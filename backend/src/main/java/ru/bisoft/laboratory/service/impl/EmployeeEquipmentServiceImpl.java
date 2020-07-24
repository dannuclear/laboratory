package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.repository.EmployeeEquipmentRepository;
import ru.bisoft.laboratory.service.EmployeeEquipmentService;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE_EQUIPMENT_ADMIN')")
public class EmployeeEquipmentServiceImpl implements EmployeeEquipmentService {

    private final EmployeeEquipmentRepository employeeEquipmentRepository;

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_WRITE') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public EmployeeEquipment create() {
        log.info("Создаем документ оборудования");
        EmployeeEquipment employeeEquipment = new EmployeeEquipment();
        return employeeEquipment;
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_READ') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public EmployeeEquipment findById(Integer id) {
        log.info("Извлекаем отдел по id {}", id);
        return employeeEquipmentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_READ') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public Page<EmployeeEquipment> findAll(Pageable pageable) {
        log.info("Извлекаем все документы оборудования постранично {}", pageable);
        return employeeEquipmentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_WRITE') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public EmployeeEquipment save(EmployeeEquipment entity) {
        log.info("Сохраняем документ оборудования {}", entity);
        return employeeEquipmentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_WRITE') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public void delete(EmployeeEquipment entity) {
        log.info("Удаляем документ оборудования {}", entity);
        employeeEquipmentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_WRITE') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public void deleteByEmployeeAndIdNotIn(Employee employee, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", employee, ids);
        if (ids.size() == 0)
            employeeEquipmentRepository.deleteByEmployee(employee);
        else
            employeeEquipmentRepository.deleteByEmployeeAndIdNotIn(employee, ids);
    }

    @Override
    public void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", equipment, ids);
        if (ids.size() == 0)
            employeeEquipmentRepository.deleteByEquipment(equipment);
        else
            employeeEquipmentRepository.deleteByEquipmentAndIdNotIn(equipment, ids);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_READ') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public Iterable<EmployeeEquipment> saveAll(Iterable<EmployeeEquipment> entities) {
        log.info("Сохраняем коллекцию документов оборудования {}", entities);
        return employeeEquipmentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_EQUIPMENT_READ') or hasRole('EMPLOYEE_EQUIPMENT_ADMIN')")
    public Page<EmployeeEquipment> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<EmployeeEquipment> findByEquipment(Equipment equipment, Pageable pageable) {
        log.info("Извлекаем всех сотрудников по оборудованию {} постранично {}", equipment, pageable);
        Page<EmployeeEquipment> result = employeeEquipmentRepository.findByEquipment(equipment, pageable);
        result.getContent().forEach(de -> {
            de.setEquipment(null);
            de.getEmployee().setEmployeeEquipments(null);
        });
        return result;
    }

    @Override
    public Page<EmployeeEquipment> findByEmployee(Employee employee, Pageable pageable) {
        log.info("Извлекаем все документы оборудования по документу {} постранично {}", employee, pageable);
        Page<EmployeeEquipment> result = employeeEquipmentRepository.findByEmployee(employee, pageable);
        result.getContent().forEach(de -> {
            de.setEmployee(null);
            de.getEquipment().setEmployeeEquipments(null);
        });
        return result;
    }
}
