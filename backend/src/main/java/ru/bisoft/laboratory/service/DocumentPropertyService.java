package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentProperty;
import ru.bisoft.laboratory.domain.Property;

import java.util.Collection;

public interface DocumentPropertyService extends GuideService<DocumentProperty> {
    Page<DocumentProperty> findByProperty(Property property, Pageable pageable);

    Page<DocumentProperty> findByDocument(Document document, Pageable pageable);

    void deleteByPropertyAndIdNotIn(Property property, Collection<Integer> ids);
}
