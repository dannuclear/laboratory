package ru.bisoft.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Selection;

@Transactional(readOnly = true)
public interface SelectionRepository extends JpaRepository<Selection, Integer> {
//	Page<Unit> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);
}
