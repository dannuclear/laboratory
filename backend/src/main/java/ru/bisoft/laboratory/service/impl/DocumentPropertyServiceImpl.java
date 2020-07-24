package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentProperty;
import ru.bisoft.laboratory.domain.Property;
import ru.bisoft.laboratory.repository.DocumentPropertyRepository;
import ru.bisoft.laboratory.service.DocumentPropertyService;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('DOCUMENT_PROPERTY_ADMIN')")
public class DocumentPropertyServiceImpl implements DocumentPropertyService {

    private final DocumentPropertyRepository documentPropertyRepository;

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_WRITE') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public DocumentProperty create() {
        log.info("Создаем документ свойства");
        DocumentProperty documentProperty = new DocumentProperty();
        return documentProperty;
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_READ') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public DocumentProperty findById(Integer id) {
        log.info("Извлекаем документ свойства по id {}", id);
        return documentPropertyRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_READ') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public Page<DocumentProperty> findAll(Pageable pageable) {
        log.info("Извлекаем все документы свойств постранично {}", pageable);
        return documentPropertyRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_WRITE') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public DocumentProperty save(DocumentProperty entity) {
        log.info("Сохраняем документ свойства {}", entity);
        return documentPropertyRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_WRITE') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public void delete(DocumentProperty entity) {
        log.info("Удаляем документ свойства {}", entity);
        documentPropertyRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_WRITE') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public void deleteByPropertyAndIdNotIn(Property property, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у свойства {} документы с id {}", property, ids);
        if (ids.size() == 0)
            documentPropertyRepository.deleteByProperty(property);
        else
            documentPropertyRepository.deleteByPropertyAndIdNotIn(property, ids);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_READ') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public Iterable<DocumentProperty> saveAll(Iterable<DocumentProperty> entities) {
        log.info("Сохраняем коллекцию документов свойств {}", entities);
        return documentPropertyRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_PROPERTY_READ') or hasRole('DOCUMENT_PROPERTY_ADMIN')")
    public Page<DocumentProperty> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<DocumentProperty> findByProperty(Property property, Pageable pageable) {
        log.info("Извлекаем все документы свойств по свойству {} постранично {}", property, pageable);
        Page<DocumentProperty> result = documentPropertyRepository.findByProperty(property, pageable);
        result.getContent().forEach(de -> {
            de.setProperty(null);
            de.getDocument().setDocumentProperties(null);
        });
        return result;
    }

    @Override
    public Page<DocumentProperty> findByDocument(Document document, Pageable pageable) {
        log.info("Извлекаем все документы свойств по документу {} постранично {}", document, pageable);
        Page<DocumentProperty> result = documentPropertyRepository.findByDocument(document, pageable);
        result.getContent().forEach(de -> {
            de.setDocument(null);
            de.getProperty().setDocumentProperties(null);
        });
        return result;
    }
}
