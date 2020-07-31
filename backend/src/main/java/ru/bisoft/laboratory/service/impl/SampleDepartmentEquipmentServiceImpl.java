package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.SampleDepartmentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.repository.SampleDepartmentEquipmentRepository;
import ru.bisoft.laboratory.service.SampleDepartmentEquipmentService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SAMPLE_DEPARTMENT_ADMIN')")
public class SampleDepartmentEquipmentServiceImpl implements SampleDepartmentEquipmentService {

    private final SampleDepartmentEquipmentRepository sampleDepartmentEquipmentRepository;

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartmentEquipment create() {
        log.info("Создаем оборудование для пробы/образца подразделения");
        SampleDepartmentEquipment entity = new SampleDepartmentEquipment();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartmentEquipment findById(Integer id) {
        log.info("Извлекаем оборудование для пробы/образца подразделения по id {}", id);
        return sampleDepartmentEquipmentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Page<SampleDepartmentEquipment> findAll(Pageable pageable) {
        log.info("Извлекаем все оборудование для пробы/образца подразделения постранично {}", pageable);
        return sampleDepartmentEquipmentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartmentEquipment save(SampleDepartmentEquipment entity) {
        log.info("Сохраняем оборудование для пробы/образца подразделения {}", entity);
        return sampleDepartmentEquipmentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public void delete(SampleDepartmentEquipment entity) {
        log.info("Удаляем оборудование для пробы/образца подразделения {}", entity);
        sampleDepartmentEquipmentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Iterable<SampleDepartmentEquipment> saveAll(Iterable<SampleDepartmentEquipment> entities) {
        log.info("Сохраняем коллекцию оборудование для пробы/образца подразделения {}", entities);
        return sampleDepartmentEquipmentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Page<SampleDepartmentEquipment> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SampleDepartmentEquipment> findByEquipment(Equipment equipment, Pageable pageable) {
        log.info("Извлекаем все оборудование для пробы/образца подразделения {} постранично {}", equipment, pageable);
        Page<SampleDepartmentEquipment> result = sampleDepartmentEquipmentRepository.findByEquipment(equipment, pageable);
        result.getContent().forEach(sp -> sp.setEquipment(null));
        return result;
    }

    @Override
    public void deleteByEquipmentAndIdNotIn(Equipment equipment, Iterable<Integer> ids) {
        sampleDepartmentEquipmentRepository.deleteByEquipmentAndIdNotIn(equipment, ids);
    }

    @Override
    public void deleteByEquipment(Equipment equipment) {
        sampleDepartmentEquipmentRepository.deleteByEquipment(equipment);
    }
}
