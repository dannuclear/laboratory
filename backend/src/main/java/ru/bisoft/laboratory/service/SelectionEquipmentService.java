package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEquipment;

public interface SelectionEquipmentService extends GuideService<SelectionEquipment> {
    Page<SelectionEquipment> findBySelection(Selection selection, Pageable pageable);

    void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids);

    void deleteBySelection(Selection selection);
}
