package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.repository.DocumentRepository;
import ru.bisoft.laboratory.service.DocumentService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('DOCUMENT_ADMIN')")
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_WRITE') or hasRole('DOCUMENT_ADMIN')")
    public Document create() {
        log.info("Создаем документ");
        Document document = new Document();
        return document;
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_READ') or hasRole('DOCUMENT_ADMIN')")
    public Document findById(Integer id) {
        log.info("Извлекаем документ по id {}", id);
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_READ') or hasRole('DOCUMENT_ADMIN')")
    public Page<Document> findAll(Pageable pageable) {
        log.info("Извлекаем все документы постранично {}", pageable);
        return documentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_WRITE') or hasRole('DOCUMENT_ADMIN')")
    public Document save(Document entity) {
        log.info("Сохраняем документ {}", entity);
        return documentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_WRITE') or hasRole('DOCUMENT_ADMIN')")
    public void delete(Document entity) {
        log.info("Удаляем документ {}", entity);
        documentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_WRITE') or hasRole('DOCUMENT_ADMIN')")
    public Iterable<Document> saveAll(Iterable<Document> entities) {
        log.info("Сохраняем коллекцию документов {}", entities);
        return documentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('DOCUMENT_READ') or hasRole('DOCUMENT_ADMIN')")
    public Page<Document> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все документы по фильтру {} постранично {}", value, pageable);
        return documentRepository.findByTitleContainsIgnoreCase(value, pageable);
    }
}
