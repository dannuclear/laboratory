package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionSample;
import ru.bisoft.laboratory.repository.SelectionSampleRepository;
import ru.bisoft.laboratory.service.SelectionSampleService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SELECTION_ADMIN')")
public class SelectionSampleServiceImpl implements SelectionSampleService {

    private final SelectionSampleRepository selectionSampleRepository;

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public SelectionSample create() {
        log.info("Создаем отбор пробы/образца");
        SelectionSample entity = new SelectionSample();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public SelectionSample findById(Integer id) {
        log.info("Извлекаем отбор пробы/образца по id {}", id);
        return selectionSampleRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Page<SelectionSample> findAll(Pageable pageable) {
        log.info("Извлекаем все отборы пробы/образца постранично {}", pageable);
        return selectionSampleRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public SelectionSample save(SelectionSample entity) {
        log.info("Сохраняем отбор пробы/образца {}", entity);
        return selectionSampleRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public void delete(SelectionSample entity) {
        log.info("Удаляем отбор пробы/образца {}", entity);
        selectionSampleRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Iterable<SelectionSample> saveAll(Iterable<SelectionSample> entities) {
        log.info("Сохраняем коллекцию отборов пробы/образца {}", entities);
        return selectionSampleRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Page<SelectionSample> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SelectionSample> findBySelection(Selection selection, Pageable pageable) {
        log.info("Извлекаем все отборы пробы/образцы для подразделения {} постранично {}", selection, pageable);
        Page<SelectionSample> result = selectionSampleRepository.findBySelection(selection, pageable);
        result.getContent().forEach(sp -> sp.setSelection(null));
        return result;
    }

    @Override
    public void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids) {
        selectionSampleRepository.deleteBySelectionAndIdNotIn(selection, ids);
    }

    @Override
    public void deleteBySelection(Selection selection) {
        selectionSampleRepository.deleteBySelection(selection);
    }
}
