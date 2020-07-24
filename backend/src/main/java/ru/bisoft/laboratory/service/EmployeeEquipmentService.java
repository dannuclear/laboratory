package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import java.util.Collection;

public interface EmployeeEquipmentService extends GuideService<EmployeeEquipment> {
    Page<EmployeeEquipment> findByEmployee(Employee employee, Pageable pageable);

    Page<EmployeeEquipment> findByEquipment(Equipment equipment, Pageable pageable);

    void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids);

    void deleteByEmployeeAndIdNotIn(Employee employee, Collection<Integer> ids);
}
