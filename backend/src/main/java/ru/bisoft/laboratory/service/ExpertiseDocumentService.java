package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.Expertise;
import ru.bisoft.laboratory.domain.ExpertiseDocument;

import java.util.Collection;

public interface ExpertiseDocumentService extends GuideService<ExpertiseDocument> {
    Page<ExpertiseDocument> findByExpertise(Expertise expertise, Pageable pageable);

    Page<ExpertiseDocument> findByDocument(Document document, Pageable pageable);

    void deleteByDocumentAndIdNotIn(Document document, Collection<Integer> ids);

    void deleteByExpertiseAndIdNotIn(Expertise expertise, Collection<Integer> ids);
}
