package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.SampleDepartment;
import ru.bisoft.laboratory.domain.SampleDepartmentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

@Transactional(readOnly = true)
public interface SampleDepartmentEquipmentRepository extends JpaRepository<SampleDepartmentEquipment, Integer> {
    @EntityGraph(attributePaths = {"sampleDepartment"})
    Page<SampleDepartmentEquipment> findByEquipment(Equipment equipment, Pageable p);

    @EntityGraph(attributePaths = {"equipment"})
    Page<SampleDepartmentEquipment> findBySampleDepartment(SampleDepartment sampleDepartment, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySampleDepartmentAndIdNotIn(SampleDepartment sampleDepartment, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySampleDepartment(SampleDepartment sampleDepartment);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEquipment(Equipment equipment);
}
