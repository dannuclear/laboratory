package ru.bisoft.laboratory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Request;

@Transactional(readOnly = true)
public interface RequestRepository extends JpaRepository<Request, Integer> {
//	@RestResource(path = "byName", rel = "byName")
//	Page<Unit> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);
}
