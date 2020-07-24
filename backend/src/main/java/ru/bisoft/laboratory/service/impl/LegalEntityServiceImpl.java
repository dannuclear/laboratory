package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.LegalEntity;
import ru.bisoft.laboratory.repository.LegalEntityRepository;
import ru.bisoft.laboratory.service.LegalEntityService;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('LEGAL_ENTITY_ADMIN')")
public class LegalEntityServiceImpl implements LegalEntityService {

    private final LegalEntityRepository legalEntityRepository;

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_WRITE') or hasRole('LEGAL_ENTITY_ADMIN')")
    public LegalEntity create() {
        log.info("Создаем единицу измерения");
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_READ') or hasRole('LEGAL_ENTITY_ADMIN')")
    public LegalEntity findById(Integer id) {
        log.info("Извлекаем единицу измерения по id {}", id);
        return legalEntityRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_READ') or hasRole('LEGAL_ENTITY_ADMIN')")
    public Page<LegalEntity> findAll(Pageable pageable) {
        return legalEntityRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_WRITE') or hasRole('LEGAL_ENTITY_ADMIN')")
    public LegalEntity save(LegalEntity entity) {
        return legalEntityRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_WRITE') or hasRole('LEGAL_ENTITY_ADMIN')")
    public void delete(LegalEntity entity) {
        legalEntityRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_WRITE') or hasRole('LEGAL_ENTITY_ADMIN')")
    public Iterable<LegalEntity> saveAll(Iterable<LegalEntity> entities) {
        return legalEntityRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('LEGAL_ENTITY_READ') or hasRole('LEGAL_ENTITY_ADMIN')")
    public Page<LegalEntity> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        return legalEntityRepository.findByNameContainsIgnoreCase(value, pageable);
    }
}
