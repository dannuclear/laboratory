package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Request;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RequestRepository extends JpaRepository<Request, Integer> {
//	@RestResource(path = "byName", rel = "byName")
//	Page<Unit> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

    @Override
    @EntityGraph(attributePaths = {"declarant"})
    Optional<Request> findById(Integer id);

    @Override
    @EntityGraph(attributePaths = {"declarant"})
    Page<Request> findAll(Pageable pageable);

}
