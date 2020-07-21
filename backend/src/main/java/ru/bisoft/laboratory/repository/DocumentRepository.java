package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Document;

@Transactional(readOnly = true)
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Page<Document> findByTitleContainsIgnoreCase(@Param("name") String name, Pageable p);
}
