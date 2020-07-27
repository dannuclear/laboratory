package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEmployee;
import ru.bisoft.laboratory.repository.SelectionEmployeeRepository;
import ru.bisoft.laboratory.service.SelectionEmployeeService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SELECTION_ADMIN')")
public class SelectionEmployeeServiceImpl implements SelectionEmployeeService {

    private final SelectionEmployeeRepository selectionEmployeeRepository;

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public SelectionEmployee create() {
        log.info("Создаем сотрудника отбора");
        SelectionEmployee entity = new SelectionEmployee();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public SelectionEmployee findById(Integer id) {
        log.info("Извлекаем сотрудника отбора по id {}", id);
        return selectionEmployeeRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Page<SelectionEmployee> findAll(Pageable pageable) {
        log.info("Извлекаем всех сотрудников отбора {}", pageable);
        return selectionEmployeeRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public SelectionEmployee save(SelectionEmployee entity) {
        log.info("Сохраняем сотрудника отбора {}", entity);
        return selectionEmployeeRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public void delete(SelectionEmployee entity) {
        log.info("Удаляем сотрудника отбора {}", entity);
        selectionEmployeeRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Iterable<SelectionEmployee> saveAll(Iterable<SelectionEmployee> entities) {
        log.info("Сохраняем коллекцию сотрудников отборов {}", entities);
        return selectionEmployeeRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Page<SelectionEmployee> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SelectionEmployee> findBySelection(Selection selection, Pageable pageable) {
        log.info("Извлекаем всех сотрудников отбора {} постранично {}", selection, pageable);
        Page<SelectionEmployee> result = selectionEmployeeRepository.findBySelection(selection, pageable);
        result.getContent().forEach(sp -> sp.setSelection(null));
        return result;
    }

    @Override
    public void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids) {
        selectionEmployeeRepository.deleteBySelectionAndIdNotIn(selection, ids);
    }

    @Override
    public void deleteBySelection(Selection selection) {
        selectionEmployeeRepository.deleteBySelection(selection);
    }
}
