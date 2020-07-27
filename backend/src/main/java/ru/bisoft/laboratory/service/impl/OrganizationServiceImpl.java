package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.repository.OrganizationRepository;
import ru.bisoft.laboratory.service.OrganizationService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ORGANIZATION_ADMIN')")
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_WRITE') or hasRole('ORGANIZATION_ADMIN')")
    public Organization create() {
        log.info("Создаем организацию");
        Organization entity = new Organization();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_READ') or hasRole('ORGANIZATION_ADMIN')")
    public Organization findById(Integer id) {
        log.info("Извлекаем организацию по id {}", id);
        return organizationRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_READ') or hasRole('ORGANIZATION_ADMIN')")
    public Page<Organization> findAll(Pageable pageable) {
        log.info("Извлекаем все организации постранично {}", pageable);
        return organizationRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_WRITE') or hasRole('ORGANIZATION_ADMIN')")
    public Organization save(Organization entity) {
        log.info("Сохраняем организацию {}", entity);
        return organizationRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_WRITE') or hasRole('ORGANIZATION_ADMIN')")
    public void delete(Organization entity) {
        log.info("Удаляем организацию {}", entity);
        organizationRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_WRITE') or hasRole('ORGANIZATION_ADMIN')")
    public Iterable<Organization> saveAll(Iterable<Organization> entities) {
        log.info("Сохраняем коллекцию организаций {}", entities);
        return organizationRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('ORGANIZATION_READ') or hasRole('ORGANIZATION_ADMIN')")
    public Page<Organization> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все организации по фильтру [{}] постранично [{}]", value, pageable);
        return organizationRepository.findByNameContainsIgnoreCase(value, pageable);
    }
}
