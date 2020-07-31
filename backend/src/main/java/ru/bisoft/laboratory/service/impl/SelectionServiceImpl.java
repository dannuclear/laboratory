package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEmployee;
import ru.bisoft.laboratory.domain.SelectionEquipment;
import ru.bisoft.laboratory.domain.SelectionSample;
import ru.bisoft.laboratory.repository.SelectionRepository;
import ru.bisoft.laboratory.repository.SelectionSampleRepository;
import ru.bisoft.laboratory.service.SelectionEmployeeService;
import ru.bisoft.laboratory.service.SelectionEquipmentService;
import ru.bisoft.laboratory.service.SelectionService;

import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SELECTION_ADMIN')")
public class SelectionServiceImpl implements SelectionService {

    private final SelectionRepository selectionRepository;
    private final SelectionEmployeeService selectionEmployeeService;
    private final SelectionEquipmentService selectionEquipmentService;
    private final SelectionSampleRepository selectionSampleRepository;

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
        Selection result = selectionRepository.save(entity);

        // Сохраняем сотрудников в рамках отбора
        if (entity.getEmployees() != null) {
            selectionEmployeeService.deleteBySelectionAndIdNotIn(entity, entity.getEmployees().stream().map(SelectionEmployee::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getEmployees().forEach(ed -> ed.setSelection(entity));
            selectionEmployeeService.saveAll(entity.getEmployees());
        }
        // Сохраняем оборудование в рамках отбора
        if (entity.getEquipments() != null) {
            selectionEquipmentService.deleteBySelectionAndIdNotIn(entity, entity.getEquipments().stream().map(SelectionEquipment::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getEquipments().forEach(ed -> ed.setSelection(entity));
            selectionEquipmentService.saveAll(entity.getEquipments());
        }
        // Сохраняем пробы по акту отбора
        if (entity.getSamples() != null) {
            if (entity.getSamples().size() == 0)
                selectionSampleRepository.deleteBySelection(entity);
            else
                selectionSampleRepository.deleteBySelectionAndIdNotIn(entity, entity.getSamples().stream().map(SelectionSample::getId).filter(Objects::nonNull).collect(Collectors.toList()));
            entity.getSamples().forEach(de -> de.setSelection(entity));
            selectionSampleRepository.saveAll(entity.getSamples());
        }
        return result;
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
