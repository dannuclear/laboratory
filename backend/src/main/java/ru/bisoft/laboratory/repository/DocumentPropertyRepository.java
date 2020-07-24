package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentProperty;
import ru.bisoft.laboratory.domain.Property;

@Transactional(readOnly = true)
public interface DocumentPropertyRepository extends JpaRepository<DocumentProperty, Integer> {
    @EntityGraph(attributePaths = {"document"})
    Page<DocumentProperty> findByProperty(Property property, Pageable p);

    @EntityGraph(attributePaths = {"property"})
    Page<DocumentProperty> findByDocument(Document document, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByPropertyAndIdNotIn(Property Property, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByProperty(Property property);
}
