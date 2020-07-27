package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.Expertise;
import ru.bisoft.laboratory.domain.ExpertiseDocument;
import ru.bisoft.laboratory.repository.ExpertiseDocumentRepository;
import ru.bisoft.laboratory.service.ExpertiseDocumentService;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EXPERTISR_DOCUMENT_ADMIN')")
public class ExpertiseDocumentServiceImpl implements ExpertiseDocumentService {

    private final ExpertiseDocumentRepository expertiseDocumentRepository;

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_WRITE') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public ExpertiseDocument create() {
        log.info("Создаем документ оборудования");
        ExpertiseDocument expertiseDocument = new ExpertiseDocument();
        return expertiseDocument;
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_READ') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public ExpertiseDocument findById(Integer id) {
        log.info("Извлекаем отдел по id {}", id);
        return expertiseDocumentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_READ') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public Page<ExpertiseDocument> findAll(Pageable pageable) {
        log.info("Извлекаем все документы оборудования постранично {}", pageable);
        return expertiseDocumentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_WRITE') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public ExpertiseDocument save(ExpertiseDocument entity) {
        log.info("Сохраняем документ оборудования {}", entity);
        return expertiseDocumentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_WRITE') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public void delete(ExpertiseDocument entity) {
        log.info("Удаляем документ оборудования {}", entity);
        expertiseDocumentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_WRITE') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public void deleteByExpertiseAndIdNotIn(Expertise expertise, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", expertise, ids);
        if (ids.size() == 0)
            expertiseDocumentRepository.deleteByExpertise(expertise);
        else
            expertiseDocumentRepository.deleteByExpertiseAndIdNotIn(expertise, ids);
    }

    @Override
    public void deleteByDocumentAndIdNotIn(Document document, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", document, ids);
        if (ids.size() == 0)
            expertiseDocumentRepository.deleteByDocument(document);
        else
            expertiseDocumentRepository.deleteByDocumentAndIdNotIn(document, ids);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_READ') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public Iterable<ExpertiseDocument> saveAll(Iterable<ExpertiseDocument> entities) {
        log.info("Сохраняем коллекцию документов оборудования {}", entities);
        return expertiseDocumentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISR_DOCUMENT_READ') or hasRole('EXPERTISR_DOCUMENT_ADMIN')")
    public Page<ExpertiseDocument> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<ExpertiseDocument> findByDocument(Document document, Pageable pageable) {
        log.info("Извлекаем всех сотрудников по оборудованию {} постранично {}", document, pageable);
        Page<ExpertiseDocument> result = expertiseDocumentRepository.findByDocument(document, pageable);
        result.getContent().forEach(de -> {
            de.setDocument(null);
            de.getExpertise().setExpertiseDocuments(null);
        });
        return result;
    }

    @Override
    public Page<ExpertiseDocument> findByExpertise(Expertise expertise, Pageable pageable) {
        log.info("Извлекаем все документы оборудования по документу {} постранично {}", expertise, pageable);
        Page<ExpertiseDocument> result = expertiseDocumentRepository.findByExpertise(expertise, pageable);
        result.getContent().forEach(de -> {
            de.setExpertise(null);
            de.getDocument().setExpertiseDocuments(null);
        });
        return result;
    }
}
