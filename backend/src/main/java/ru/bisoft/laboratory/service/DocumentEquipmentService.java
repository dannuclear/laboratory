package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import java.util.Collection;

public interface DocumentEquipmentService extends GuideService<DocumentEquipment> {
    Page<DocumentEquipment> findByEquipment(Equipment equipment, Pageable pageable);

    Page<DocumentEquipment> findByDocument(Document document, Pageable pageable);

    void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids);
}
