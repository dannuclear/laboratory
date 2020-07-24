package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.Expertise;
import ru.bisoft.laboratory.domain.ExpertiseDocument;

@Transactional(readOnly = true)
public interface ExpertiseDocumentRepository extends JpaRepository<ExpertiseDocument, Integer> {
    @EntityGraph(attributePaths = {"expertise"})
    Page<ExpertiseDocument> findByDocument(Document document, Pageable p);

    @EntityGraph(attributePaths = {"document"})
    Page<ExpertiseDocument> findByExpertise(Expertise expertise, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByExpertiseAndIdNotIn(Expertise expertise, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByExpertise(Expertise expertise);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByDocumentAndIdNotIn(Document document, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByDocument(Document document);
}
