package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Selection;

import java.util.Optional;

@Transactional(readOnly = true)
public interface SelectionRepository extends JpaRepository<Selection, Integer> {
//	Page<Unit> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);
    @Override
    @EntityGraph(attributePaths = {"request.declarant"})
    Optional<Selection> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = {"request.declarant"})
    Page<Selection> findAll(Pageable pageable);
}
