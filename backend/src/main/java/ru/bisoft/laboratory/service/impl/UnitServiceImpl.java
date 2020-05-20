package ru.bisoft.laboratory.service.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.Unit;
import ru.bisoft.laboratory.repository.UnitRepository;
import ru.bisoft.laboratory.service.UnitService;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('UNIT_ADMIN')")
public class UnitServiceImpl implements UnitService {

	private final UnitRepository unitRepository;

	@Override
	@PreAuthorize("hasAuthority('UNIT_WRITE') or hasRole('UNIT_ADMIN')")
	public Unit create() {
		log.info("Создаем единицу измерения");
		return null;
	}

	@Override
	@PreAuthorize("hasAuthority('UNIT_READ') or hasRole('UNIT_ADMIN')")
	public Unit findById(Integer id) {
		log.info("Извлекаем единицу измерения по id {}", id);
		return unitRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('UNIT_READ') or hasRole('UNIT_ADMIN')")
	public Page<Unit> findAll(Pageable pageable) {
		return unitRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('UNIT_WRITE') or hasRole('UNIT_ADMIN')")
	public Unit save(Unit entity) {
		return unitRepository.save(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('UNIT_WRITE') or hasRole('UNIT_ADMIN')")
	public void delete(Unit entity) {
		unitRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('UNIT_WRITE') or hasRole('UNIT_ADMIN')")
	public Iterable<Unit> saveAll(Iterable<Unit> entities) {
		return unitRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('UNIT_READ') or hasRole('UNIT_ADMIN')")
	public Page<Unit> findByString(String value, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
