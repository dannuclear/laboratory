package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Protocol;

import java.util.Optional;

@Transactional(readOnly = true)
public interface ProtocolRepository extends JpaRepository<Protocol, Integer> {

    @Override
    @EntityGraph(attributePaths = {"selection"})
    Page<Protocol> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"selection"})
    Optional<Protocol> findById(Integer id);
//	Page<Department> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);
//
//	Page<Department> findByOrganization(Organization organization, Pageable p);

}
