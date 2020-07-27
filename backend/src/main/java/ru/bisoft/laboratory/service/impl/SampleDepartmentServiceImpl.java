package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleDepartment;
import ru.bisoft.laboratory.repository.SampleDepartmentRepository;
import ru.bisoft.laboratory.service.SampleDepartmentService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SAMPLE_DEPARTMENT_ADMIN')")
public class SampleDepartmentServiceImpl implements SampleDepartmentService {

    private final SampleDepartmentRepository sampleDepartmentRepository;

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartment create() {
        log.info("Создаем пробу/образец для подразделения");
        SampleDepartment entity = new SampleDepartment();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartment findById(Integer id) {
        log.info("Извлекаем пробу/образец для подразделения по id {}", id);
        return sampleDepartmentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Page<SampleDepartment> findAll(Pageable pageable) {
        log.info("Извлекаем все пробы/образцы для подразделения постранично {}", pageable);
        return sampleDepartmentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartment save(SampleDepartment entity) {
        log.info("Сохраняем пробы/образцы подразделения {}", entity);
        return sampleDepartmentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public void delete(SampleDepartment entity) {
        log.info("Удаляем пробу/образец для подразделения {}", entity);
        sampleDepartmentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Iterable<SampleDepartment> saveAll(Iterable<SampleDepartment> entities) {
        log.info("Сохраняем коллекцию проб/образцов для подразделения {}", entities);
        return sampleDepartmentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Page<SampleDepartment> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SampleDepartment> findBySample(Sample sample, Pageable pageable) {
        log.info("Извлекаем все пробы/образцы для подразделения {} постранично {}", sample, pageable);
        Page<SampleDepartment> result = sampleDepartmentRepository.findBySample(sample, pageable);
        result.getContent().forEach(sp -> sp.setSample(null));
        return result;
    }

    @Override
    public void deleteBySampleAndIdNotIn(Sample sample, Iterable<Integer> ids) {
        sampleDepartmentRepository.deleteBySampleAndIdNotIn(sample, ids);
    }

    @Override
    public void deleteBySample(Sample sample) {
        sampleDepartmentRepository.deleteBySample(sample);
    }
}
