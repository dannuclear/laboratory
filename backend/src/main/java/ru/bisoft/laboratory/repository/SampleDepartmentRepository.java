package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Department;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleDepartment;

@Transactional(readOnly = true)
public interface SampleDepartmentRepository extends JpaRepository<SampleDepartment, Integer> {
    @EntityGraph(attributePaths = {"department"})
    Page<SampleDepartment> findBySample(Sample sample, Pageable p);

    @EntityGraph(attributePaths = {"sample"})
    Page<SampleDepartment> findByDepartment(Department department, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySampleAndIdNotIn(Sample sample, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySample(Sample sample);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByDepartmentAndIdNotIn(Department department, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByDepartment(Department department);
}
