package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Sample;

@Transactional(readOnly = true)
public interface SampleRepository extends JpaRepository<Sample, Integer> {
    Page<Sample> findByCodeContainsIgnoreCase(@Param("code") String code, Pageable p);
}
