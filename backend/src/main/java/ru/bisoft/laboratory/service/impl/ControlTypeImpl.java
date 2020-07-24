package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.ControlType;
import ru.bisoft.laboratory.repository.ControlTypeRepository;
import ru.bisoft.laboratory.service.ControlTypeService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CONTROLTYPE_ADMIN')")
public class ControlTypeImpl implements ControlTypeService {

    ControlTypeRepository controlTypeRepository;

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_WRITE') or hasRole('CONTROLTYPE_ADMIN')")
    public ControlType create() {
        log.info("Создаем тип контроля");
        ControlType controlType = new ControlType();
        return controlType;
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_READ') or hasRole('CONTROLTYPE_ADMIN')")
    public ControlType findById(Integer id) {
        log.info("Извлекаем тип контроля по id {}", id);
        return controlTypeRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_READ') or hasRole('CONTROLTYPE_ADMIN')")
    public Page<ControlType> findAll(Pageable pageable) {
        log.info("Извлекаем все тип контроля постранично {}", pageable);
        return controlTypeRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_READ') or hasRole('CONTROLTYPE_ADMIN')")
    public Page<ControlType> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все тип контроля по фильтру {} постранично {}", value, pageable);
        return controlTypeRepository.findByNameContainsIgnoreCase(value, pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_WRITE') or hasRole('CONTROLTYPE_ADMIN')")
    public ControlType save(ControlType entity) {
        log.info("Сохраняем тип контроля {}", entity);
        return controlTypeRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_WRITE') or hasRole('CONTROLTYPE_ADMIN')")
    public Iterable<ControlType> saveAll(Iterable<ControlType> entities) {
        log.info("Сохраняем коллекцию типов контроля {}", entities);
        return controlTypeRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_WRITE') or hasRole('CONTROLTYPE_ADMIN')")
    public void delete(ControlType entity) {
        log.info("Удаляем тип контроля {}", entity);
        controlTypeRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('CONTROLTYPE_READ') or hasRole('CONTROLTYPE_ADMIN')")
    public Page<ControlType> findByAbbreviation(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все тип контроля по фильтру {} постранично {}", value, pageable);
        return controlTypeRepository.findByAbbreviationContainsIgnoreCase(value, pageable);
    }
}
