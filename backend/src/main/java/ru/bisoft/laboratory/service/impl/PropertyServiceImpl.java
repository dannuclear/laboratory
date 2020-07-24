package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.Property;
import ru.bisoft.laboratory.repository.PropertyRepository;
import ru.bisoft.laboratory.service.PropertyService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('PROPERTY_ADMIN')")
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_WRITE') or hasRole('PROPERTY_ADMIN')")
    public Property create() {
        log.info("Создаем показатель");
        Property property = new Property();
        return property;
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_READ') or hasRole('PROPERTY_ADMIN')")
    public Property findById(Integer id) {
        log.info("Извлекаем показатель по id {}", id);
        return propertyRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_READ') or hasRole('PROPERTY_ADMIN')")
    public Page<Property> findAll(Pageable pageable) {
        log.info("Извлекаем все показатели постранично {}", pageable);
        return propertyRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_WRITE') or hasRole('PROPERTY_ADMIN')")
    public Property save(Property entity) {
        log.info("Сохраняем показатель {}", entity);
        return propertyRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_WRITE') or hasRole('PROPERTY_ADMIN')")
    public void delete(Property entity) {
        log.info("Удаляем документ {}", entity);
        propertyRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_WRITE') or hasRole('PROPERTY_ADMIN')")
    public Iterable<Property> saveAll(Iterable<Property> entities) {
        log.info("Сохраняем коллекцию показателей {}", entities);
        return propertyRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_READ') or hasRole('PROPERTY_ADMIN')")
    public Page<Property> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все показатели по фильтру {} постранично {}", value, pageable);
        return propertyRepository.findByNameContainsIgnoreCase(value, pageable);
    }
}
