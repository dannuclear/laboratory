package ru.bisoft.laboratory.service.impl;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.repository.DocumentEquipmentRepository;
import ru.bisoft.laboratory.service.DocumentEquipmentService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('DOCUMENT_EQUIPMENT_ADMIN')")
public class DocumentEquipmentServiceImpl implements DocumentEquipmentService {

	private final DocumentEquipmentRepository documentEquipmentRepository;

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_WRITE') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public DocumentEquipment create() {
		log.info("Создаем документ оборудования");
		DocumentEquipment documentEquipment = new DocumentEquipment();
		return documentEquipment;
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_READ') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public DocumentEquipment findById(Integer id) {
		log.info("Извлекаем отдел по id {}", id);
		return documentEquipmentRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_READ') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public Page<DocumentEquipment> findAll(Pageable pageable) {
		log.info("Извлекаем все документы оборудования постранично {}", pageable);
		return documentEquipmentRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_WRITE') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public DocumentEquipment save(DocumentEquipment entity) {
		log.info("Сохраняем документ оборудования {}", entity);
		return documentEquipmentRepository.save(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_WRITE') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public void delete(DocumentEquipment entity) {
		log.info("Удаляем документ оборудования {}", entity);
		documentEquipmentRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_WRITE') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids) {
		if (ids == null)
			throw new IllegalArgumentException("ids не может быть пустым");
		log.info("Удаляем у оборудования {} документы с id {}", equipment, ids);
		if (ids.size() == 0)
			documentEquipmentRepository.deleteByEquipment(equipment);
		else
			documentEquipmentRepository.deleteByEquipmentAndIdNotIn(equipment, ids);
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_READ') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public Iterable<DocumentEquipment> saveAll(Iterable<DocumentEquipment> entities) {
		log.info("Сохраняем коллекцию документов оборудования {}", entities);
		return documentEquipmentRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('DOCUMENT_EQUIPMENT_READ') or hasRole('DOCUMENT_EQUIPMENT_ADMIN')")
	public Page<DocumentEquipment> findByString(String value, Pageable pageable) {
		throw new UnsupportedOperationException("Неподдерживаемая опрация");
	}

	@Override
	public Page<DocumentEquipment> findByEquipment(Equipment equipment, Pageable pageable) {
		log.info("Извлекаем все документы оборудования по оборудованию {} постранично {}", equipment, pageable);
		Page<DocumentEquipment> result = documentEquipmentRepository.findByEquipment(equipment, pageable);
		result.getContent().forEach(de -> {
			de.setEquipment(null);
			de.getDocument().setDocumentEquipments(null);
		});
		return result;
	}

	@Override
	public Page<DocumentEquipment> findByDocument(Document document, Pageable pageable) {
		log.info("Извлекаем все документы оборудования по документу {} постранично {}", document, pageable);
		Page<DocumentEquipment> result = documentEquipmentRepository.findByDocument(document, pageable);
		result.getContent().forEach(de -> {
			de.setDocument(null);
			de.getEquipment().setDocumentEquipments(null);
		});
		return result;
	}
}
