package ru.bisoft.laboratory.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.Organization;
import ru.bisoft.laboratory.repository.EmployeeRepository;
import ru.bisoft.laboratory.service.EmployeeService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE_ADMIN')")
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository departmentRepository;

	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
	public Employee create() {
		log.info("Создаем служащего");
		Employee department = new Employee();
		return department;
	}
	
	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_READ') or hasRole('EMPLOYEE_ADMIN')")
	public Employee findById(Integer id) {
		log.info("Извлекаем сотрудника по id {}", id);
		return departmentRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_READ') or hasRole('EMPLOYEE_ADMIN')")
	public Page<Employee> findAll(Pageable pageable) {
		log.info("Извлекаем все служащегоы постранично {}", pageable);
		return departmentRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
	public Employee save(Employee entity) {
		log.info("Сохраняем служащего {}", entity);
		return departmentRepository.save(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
	public void delete(Employee entity) {
		log.info("Удаляем служащего {}", entity);
		departmentRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_WRITE') or hasRole('EMPLOYEE_ADMIN')")
	public Iterable<Employee> saveAll(Iterable<Employee> entities) {
		log.info("Сохраняем коллекцию служащегоов {}", entities);
		return departmentRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('EMPLOYEE_READ') or hasRole('EMPLOYEE_ADMIN')")
	public Page<Employee> findByString(String value, Pageable pageable) {
		if (StringUtils.isEmpty(value))
			return findAll(pageable);
		log.info("Извлекаем все служащих по фильтру {} постранично {}", value, pageable);
		return departmentRepository.findByNameContainsIgnoreCase(value, pageable);
	}

	@Override
	public Page<Employee> findByOrganization(Organization organization, Pageable pageable) {
		log.info("Извлекаем всех служащих организации {} постранично {}", organization, pageable);
//		Page<Employee> result = employeeRepository.findByOrganization(organization, pageable);
//		result.getContent().forEach(sp -> sp.setOrganization(null));
		return null;
	}
}
