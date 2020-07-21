package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.bisoft.laboratory.domain.Department;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.repository.DepartmentRepository;
import ru.bisoft.laboratory.service.DepartmentService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('DEPARTMENT_ADMIN')")
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_WRITE') or hasRole('DEPARTMENT_ADMIN')")
    public Department create() {
        log.info("Создаем отдел");
        Department department = new Department();
        return department;
    }

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_READ') or hasRole('DEPARTMENT_ADMIN')")
    public Department findById(Integer id) {
        log.info("Извлекаем отдел по id {}", id);
        return departmentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_READ') or hasRole('DEPARTMENT_ADMIN')")
    public Page<Department> findAll(Pageable pageable) {
        log.info("Извлекаем все отделы постранично {}", pageable);
        return departmentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_WRITE') or hasRole('DEPARTMENT_ADMIN')")
    public Department save(Department entity) {
        log.info("Сохраняем отдел {}", entity);
        return departmentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_WRITE') or hasRole('DEPARTMENT_ADMIN')")
    public void delete(Department entity) {
        log.info("Удаляем отдел {}", entity);
        departmentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_WRITE') or hasRole('DEPARTMENT_ADMIN')")
    public Iterable<Department> saveAll(Iterable<Department> entities) {
        log.info("Сохраняем коллекцию отделов {}", entities);
        return departmentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_READ') or hasRole('DEPARTMENT_ADMIN')")
    public Page<Department> findByString(String value, Pageable pageable) {
        if (StringUtils.isEmpty(value))
            return findAll(pageable);
        log.info("Извлекаем все отделы по фильтру {} постранично {}", value, pageable);
        return departmentRepository.findByNameContainsIgnoreCase(value, pageable);
    }

    @Override
    public Page<Department> findByOrganization(Organization organization, Pageable pageable) {
        log.info("Извлекаем все подразделения организации {} постранично {}", organization, pageable);
        Page<Department> result = departmentRepository.findByOrganization(organization, pageable);
        result.getContent().forEach(sp -> sp.setOrganization(null));
        return result;
    }
}
