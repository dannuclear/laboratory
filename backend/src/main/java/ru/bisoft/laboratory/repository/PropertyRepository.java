package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Property;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    @EntityGraph(value = "property.allJoins")
    Page<Property> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

    @Override
    @EntityGraph(value = "property.allJoins")
    List<Property> findAll();

    @Override
    @EntityGraph(value = "property.allJoins")
    Page<Property> findAll(Pageable pageable);

    @Override
    @EntityGraph(value = "property.allJoins")
    Optional<Property> findById(Integer id);
}
