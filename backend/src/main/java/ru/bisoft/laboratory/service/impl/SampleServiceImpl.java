package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;
import ru.bisoft.laboratory.repository.SampleRepository;
import ru.bisoft.laboratory.service.SamplePropertyService;
import ru.bisoft.laboratory.service.SampleService;

import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SAMPLE_ADMIN')")
public class SampleServiceImpl implements SampleService {

    private final SampleRepository sampleRepository;
    private final SamplePropertyService samplePropertyService;

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_WRITE') or hasRole('SAMPLE_ADMIN')")
    public Sample create() {
        log.info("Создаем пробу/образец");
        Sample sample = new Sample();
        return sample;
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_READ') or hasRole('SAMPLE_ADMIN')")
    public Sample findById(Integer id) {
        log.info("Извлекаем пробу/образец по id {}", id);
        return sampleRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_READ') or hasRole('SAMPLE_ADMIN')")
    public Page<Sample> findAll(Pageable pageable) {
        log.info("Извлекаем все пробы/образцы постранично {}", pageable);
        return sampleRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_WRITE') or hasRole('SAMPLE_ADMIN')")
    public Sample save(Sample entity) {
        log.info("Сохраняем пробы/образцы {}", entity);
        Sample result = sampleRepository.save(entity);

        // Сохраняем показатели пробы по пробе
        if (entity.getProperties() != null) {
            if (entity.getProperties().size() == 0)
                samplePropertyService.deleteBySample(entity);
            else
                samplePropertyService.deleteBySampleAndIdNotIn(entity, entity.getProperties().stream().map(SampleProperty::getId).collect(Collectors.toList()));
            entity.getProperties().forEach(de -> de.setSample(entity));
            samplePropertyService.saveAll(entity.getProperties());
        }
        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_WRITE') or hasRole('SAMPLE_ADMIN')")
    public void delete(Sample entity) {
        log.info("Удаляем пробы/образцы {}", entity);
        sampleRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_READ') or hasRole('SAMPLE_ADMIN')")
    public Iterable<Sample> saveAll(Iterable<Sample> entities) {
        log.info("Сохраняем коллекцию пробы/образцов {}", entities);
        return sampleRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_READ') or hasRole('SAMPLE_ADMIN')")
    public Page<Sample> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все пробы/образцы по фильтру {} постранично {}", value, pageable);
        return sampleRepository.findByCodeContainsIgnoreCase(value, pageable);
    }
}
