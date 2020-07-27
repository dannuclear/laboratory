package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEquipment;
import ru.bisoft.laboratory.repository.SelectionEquipmentRepository;
import ru.bisoft.laboratory.service.SelectionEquipmentService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SELECTION_ADMIN')")
public class SelectionEquipmentServiceImpl implements SelectionEquipmentService {

    private final SelectionEquipmentRepository selectionEquipmentRepository;

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public SelectionEquipment create() {
        log.info("Создаем оборудование отбора");
        SelectionEquipment entity = new SelectionEquipment();
        return entity;
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public SelectionEquipment findById(Integer id) {
        log.info("Извлекаем оборудование отбора по id {}", id);
        return selectionEquipmentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Page<SelectionEquipment> findAll(Pageable pageable) {
        log.info("Извлекаем все оборудование отбора {}", pageable);
        return selectionEquipmentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public SelectionEquipment save(SelectionEquipment entity) {
        log.info("Сохраняем оборудование отбора {}", entity);
        return selectionEquipmentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_WRITE') or hasRole('SELECTION_ADMIN')")
    public void delete(SelectionEquipment entity) {
        log.info("Удаляем оборудование отбора {}", entity);
        selectionEquipmentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Iterable<SelectionEquipment> saveAll(Iterable<SelectionEquipment> entities) {
        log.info("Сохраняем коллекцию оборудования отборов {}", entities);
        return selectionEquipmentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('SELECTION_READ') or hasRole('SELECTION_ADMIN')")
    public Page<SelectionEquipment> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<SelectionEquipment> findBySelection(Selection selection, Pageable pageable) {
        log.info("Извлекаем все оборудование отбора {} постранично {}", selection, pageable);
        Page<SelectionEquipment> result = selectionEquipmentRepository.findBySelection(selection, pageable);
        result.getContent().forEach(sp -> sp.setSelection(null));
        return result;
    }

    @Override
    public void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids) {
        selectionEquipmentRepository.deleteBySelectionAndIdNotIn(selection, ids);
    }

    @Override
    public void deleteBySelection(Selection selection) {
        selectionEquipmentRepository.deleteBySelection(selection);
    }
}
