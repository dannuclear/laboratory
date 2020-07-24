package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.PropertyType;
import ru.bisoft.laboratory.repository.PropertyTypeRepository;
import ru.bisoft.laboratory.service.PropertyTypeService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('PROPERTY_TYPE_ADMIN')")
public class PropertyTypeServiceImpl implements PropertyTypeService {

    private final PropertyTypeRepository propertyTypeRepository;

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_WRITE') or hasRole('PROPERTY_TYPE_ADMIN')")
    public PropertyType create() {
        log.info("Создаем вид показателя");
        PropertyType propertyType = new PropertyType();
        return propertyType;
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_READ') or hasRole('PROPERTY_TYPE_ADMIN')")
    public PropertyType findById(Integer id) {
        log.info("Извлекаем вид показателя по id {}", id);
        return propertyTypeRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_READ') or hasRole('PROPERTY_TYPE_ADMIN')")
    public Page<PropertyType> findAll(Pageable pageable) {
        log.info("Извлекаем все виды показателей постранично {}", pageable);
        return propertyTypeRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_WRITE') or hasRole('PROPERTY_TYPE_ADMIN')")
    public PropertyType save(PropertyType entity) {
        log.info("Сохраняем вид показателя {}", entity);
        return propertyTypeRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_WRITE') or hasRole('PROPERTY_TYPE_ADMIN')")
    public void delete(PropertyType entity) {
        log.info("Удаляем вид показателя {}", entity);
        propertyTypeRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_WRITE') or hasRole('PROPERTY_TYPE_ADMIN')")
    public Iterable<PropertyType> saveAll(Iterable<PropertyType> entities) {
        log.info("Сохраняем коллекцию видов показателей {}", entities);
        return propertyTypeRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('PROPERTY_TYPE_READ') or hasRole('PROPERTY_TYPE_ADMIN')")
    public Page<PropertyType> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все виды показатели по фильтру {} постранично {}", value, pageable);
        return propertyTypeRepository.findByNameContainsIgnoreCase(value, pageable);
    }
}
