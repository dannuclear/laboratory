package ru.bisoft.laboratory.service.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.repository.SelectionRepository;
import ru.bisoft.laboratory.service.SelectionService;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SELECTION_ADMIN')")
public class SelectionServiceImpl implements SelectionService {

	private final SelectionRepository selectionRepository;

	@Override
	@PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
	public Selection create() {
		log.info("Создаем отбор");
		return null;
	}

	@Override
	@PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
	public Selection findById(Integer id) {
		log.info("Извлекаем заявку по id {}", id);
		return selectionRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
	public Page<Selection> findAll(Pageable pageable) {
		return selectionRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
	public Selection save(Selection entity) {
		return selectionRepository.save(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
	public void delete(Selection entity) {
		selectionRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
	public Iterable<Selection> saveAll(Iterable<Selection> entities) {
		return selectionRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
	public Page<Selection> findByString(String value, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
