package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Expertise;
import ru.bisoft.laboratory.domain.Protocol;

@Transactional(readOnly = true)
public interface ExpertiseRepository extends JpaRepository<Expertise, Integer> {
    Page<Expertise> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

    @EntityGraph(attributePaths = {"protocol"})
    Page<Expertise> findByProtocol(Protocol protocol, Pageable p);
}
