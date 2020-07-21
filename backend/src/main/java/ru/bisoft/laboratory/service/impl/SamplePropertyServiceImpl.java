package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;
import ru.bisoft.laboratory.repository.SamplePropertyRepository;
import ru.bisoft.laboratory.service.SamplePropertyService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SAMPLE_PROPERTY_ADMIN')")
public class SamplePropertyServiceImpl implements SamplePropertyService {

    private final SamplePropertyRepository samplePropertyRepository;

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_WRITE') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public SampleProperty create() {
        log.info("Создаем показатель пробы/образца");
        SampleProperty sampleProperty = new SampleProperty();
        return sampleProperty;
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_READ') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public SampleProperty findById(Integer id) {
        log.info("Извлекаем показатель пробы/образца по id {}", id);
        return samplePropertyRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_READ') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public Page<SampleProperty> findAll(Pageable pageable) {
        log.info("Извлекаем все показатели пробы/образца постранично {}", pageable);
        return samplePropertyRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_WRITE') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public SampleProperty save(SampleProperty entity) {
        log.info("Сохраняем пробы/образцы {}", entity);
        return samplePropertyRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_WRITE') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public void delete(SampleProperty entity) {
        log.info("Удаляем показатель пробы/образца {}", entity);
        samplePropertyRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_READ') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public Iterable<SampleProperty> saveAll(Iterable<SampleProperty> entities) {
        log.info("Сохраняем коллекцию показателей пробы/образа {}", entities);
        return samplePropertyRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_PROPERTY_READ') or hasRole('SAMPLE_PROPERTY_ADMIN')")
    public Page<SampleProperty> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SampleProperty> findBySample(Sample sample, Pageable pageable) {
        log.info("Извлекаем все показатели пробы/образцы {} постранично {}", sample, pageable);
        Page<SampleProperty> result = samplePropertyRepository.findBySample(sample, pageable);
        result.getContent().forEach(sp -> sp.setSample(null));
        return result;
    }

    @Override
    public void deleteBySampleAndIdNotIn(Sample sample, Iterable<Integer> ids) {
        samplePropertyRepository.deleteBySampleAndIdNotIn(sample, ids);
    }

    @Override
    public void deleteBySample(Sample sample) {
        samplePropertyRepository.deleteBySample(sample);
    }
}
