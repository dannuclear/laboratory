package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.ControlType;

@Transactional(readOnly = true)
public interface ControlTypeRepository extends JpaRepository<ControlType, Integer> {

    Page<ControlType> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

    Page<ControlType> findByAbbreviationContainsIgnoreCase(@Param("abbreviation") String abbreviation, Pageable p);
}
