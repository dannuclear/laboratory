package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.Expertise;
import ru.bisoft.laboratory.domain.ExpertiseDocument;
import ru.bisoft.laboratory.repository.ExpertiseRepository;
import ru.bisoft.laboratory.service.ExpertiseDocumentService;
import ru.bisoft.laboratory.service.ExpertiseService;

import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EXPERTISE_ADMIN')")
public class ExpertiseServiceImpl implements ExpertiseService {

    private final ExpertiseRepository expertiseRepository;
    private final ExpertiseDocumentService expertiseDocumentService;

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_WRITE') or hasRole('EXPERTISE_ADMIN')")
    public Expertise create() {
        log.info("Создаем оборудование");
        Expertise equipment = new Expertise();
        return equipment;
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_READ') or hasRole('EXPERTISE_ADMIN')")
    public Expertise findById(Integer id) {
        log.info("Извлекаем оборудование по id {}", id);
        return expertiseRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_READ') or hasRole('EXPERTISE_ADMIN')")
    public Page<Expertise> findAll(Pageable pageable) {
        log.info("Извлекаем все оборудование постранично {}", pageable);
        return expertiseRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_WRITE') or hasRole('EXPERTISE_ADMIN')")
    public Expertise save(Expertise entity) {
        log.info("Сохраняем оборудование {}", entity);
        Expertise result = expertiseRepository.save(entity);
        // Сохраняем документы по оборудованию
        if (entity.getExpertiseDocuments() != null) {
            expertiseDocumentService.deleteByExpertiseAndIdNotIn(entity, entity.getExpertiseDocuments().stream().map(ExpertiseDocument::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getExpertiseDocuments().forEach(de -> de.setExpertise(entity));
            expertiseDocumentService.saveAll(entity.getExpertiseDocuments());
        }
        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_WRITE') or hasRole('EXPERTISE_ADMIN')")
    public void delete(Expertise entity) {
        log.info("Удаляем оборудование {}", entity);
        expertiseRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_WRITE') or hasRole('EXPERTISE_ADMIN')")
    public Iterable<Expertise> saveAll(Iterable<Expertise> entities) {
        log.info("Сохраняем коллекцию оборудование {}", entities);
        return expertiseRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EXPERTISE_READ') or hasRole('EXPERTISE_ADMIN')")
    public Page<Expertise> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все оборудование по фильтру [{}] постранично [{}]", value, pageable);
        return expertiseRepository.findByNameContainsIgnoreCase(value, pageable);
    }
}
