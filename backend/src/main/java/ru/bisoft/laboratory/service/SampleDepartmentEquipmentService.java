package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.SampleDepartmentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

public interface SampleDepartmentEquipmentService extends GuideService<SampleDepartmentEquipment> {

    Page<SampleDepartmentEquipment> findByEquipment(Equipment equipment, Pageable pageable);

    void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids);

    void deleteByEquipment(Equipment equipment);
}
