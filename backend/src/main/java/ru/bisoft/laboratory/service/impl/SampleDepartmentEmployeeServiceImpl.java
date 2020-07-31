package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.SampleDepartmentEmployee;
import ru.bisoft.laboratory.repository.SampleDepartmentEmployeeRepository;
import ru.bisoft.laboratory.service.SampleDepartmentEmployeeService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SAMPLE_DEPARTMENT_ADMIN')")
public class SampleDepartmentEmployeeServiceImpl implements SampleDepartmentEmployeeService {

    private final SampleDepartmentEmployeeRepository sampleDepartmentEmployeeRepository;

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartmentEmployee create() {
        log.info("Создаем сотрудника для пробы/образца подразделения");
        SampleDepartmentEmployee entity = new SampleDepartmentEmployee();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartmentEmployee findById(Integer id) {
        log.info("Извлекаем сотрудника для пробы/образца подразделения по id {}", id);
        return sampleDepartmentEmployeeRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Page<SampleDepartmentEmployee> findAll(Pageable pageable) {
        log.info("Извлекаем всех сотрудников для пробы/образца подразделения постранично {}", pageable);
        return sampleDepartmentEmployeeRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public SampleDepartmentEmployee save(SampleDepartmentEmployee entity) {
        log.info("Сохраняем сотрудника для пробы/образца подразделения {}", entity);
        return sampleDepartmentEmployeeRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_WRITE') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public void delete(SampleDepartmentEmployee entity) {
        log.info("Удаляем сотрудника для пробы/образца подразделения {}", entity);
        sampleDepartmentEmployeeRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Iterable<SampleDepartmentEmployee> saveAll(Iterable<SampleDepartmentEmployee> entities) {
        log.info("Сохраняем коллекцию сотрудников для пробы/образца подразделения {}", entities);
        return sampleDepartmentEmployeeRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SAMPLE_DEPARTMENT_READ') or hasRole('SAMPLE_DEPARTMENT_ADMIN')")
    public Page<SampleDepartmentEmployee> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SampleDepartmentEmployee> findByEmployee(Employee employee, Pageable pageable) {
        log.info("Извлекаем всех сотрудников для пробы/образца подразделения {} постранично {}", employee, pageable);
        Page<SampleDepartmentEmployee> result = sampleDepartmentEmployeeRepository.findByEmployee(employee, pageable);
        result.getContent().forEach(sp -> sp.setEmployee(null));
        return result;
    }

    @Override
    public void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids) {
        sampleDepartmentEmployeeRepository.deleteByEmployeeAndIdNotIn(employee, ids);
    }

    @Override
    public void deleteByEmployee(Employee employee) {
        sampleDepartmentEmployeeRepository.deleteByEmployee(employee);
    }
}
