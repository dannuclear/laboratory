package ru.bisoft.laboratory.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.ObjectClass;
import ru.bisoft.laboratory.domain.ObjectClassDocument;
import ru.bisoft.laboratory.domain.ObjectClassEquipment;
import ru.bisoft.laboratory.domain.ObjectClassProperty;
import ru.bisoft.laboratory.repository.ObjectClassDocumentRepository;
import ru.bisoft.laboratory.repository.ObjectClassEquipmentRepository;
import ru.bisoft.laboratory.repository.ObjectClassPropertyRepository;
import ru.bisoft.laboratory.repository.ObjectClassRepository;
import ru.bisoft.laboratory.service.ObjectClassService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('OBJECT_CLASS_ADMIN')")
public class ObjectClassServiceImpl implements ObjectClassService {

	private final ObjectClassRepository objectClassRepository;
	private final ObjectClassDocumentRepository objectClassDocumentRepository;
	private final ObjectClassPropertyRepository objectClassPropertyRepository;
	private final ObjectClassEquipmentRepository objectClassEquipmentRepository;

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_WRITE') or hasRole('OBJECT_CLASS_ADMIN')")
	public ObjectClass create() {
		log.info("Создаем класс объктов");
		ObjectClass objectClass = new ObjectClass();
		return objectClass;
	}

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_READ') or hasRole('OBJECT_CLASS_ADMIN')")
	public ObjectClass findById(Integer id) {
		log.info("Извлекаем класс объектов по id {}", id);
		return objectClassRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_READ') or hasRole('OBJECT_CLASS_ADMIN')")
	public Page<ObjectClass> findAll(Pageable pageable) {
		log.info("Извлекаем все классы объктов постранично {}", pageable);
		return objectClassRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_WRITE') or hasRole('OBJECT_CLASS_ADMIN')")
	public ObjectClass save(ObjectClass entity) {
		log.info("Сохраняем класс объктов {}", entity);
		ObjectClass result = objectClassRepository.save(entity);
		// Сохраняем документы по классу объекта
		if (entity.getDocuments() != null) {
			if (entity.getDocuments().size() == 0)
				objectClassDocumentRepository.deleteByObjectClass(entity);
			else
				objectClassDocumentRepository.deleteByObjectClassAndIdNotIn(entity, entity.getDocuments().stream().map(ObjectClassDocument::getId).collect(Collectors.toList()));
			entity.getDocuments().forEach(de -> de.setObjectClass(entity));
			objectClassDocumentRepository.saveAll(entity.getDocuments());
		}

		// Сохраняем показатели по классу объекта
		if (entity.getProperties() != null) {
			if (entity.getProperties().size() == 0)
				objectClassPropertyRepository.deleteByObjectClass(entity);
			else
				objectClassPropertyRepository.deleteByObjectClassAndIdNotIn(entity, entity.getProperties().stream().map(ObjectClassProperty::getId).collect(Collectors.toList()));
			entity.getProperties().forEach(de -> de.setObjectClass(entity));
			objectClassPropertyRepository.saveAll(entity.getProperties());
		}

		// Сохраняем оборудование
		if (entity.getEquipments() != null) {
			if (entity.getEquipments().size() == 0)
				objectClassEquipmentRepository.deleteByObjectClass(entity);
			else
				objectClassEquipmentRepository.deleteByObjectClassAndIdNotIn(entity, entity.getEquipments().stream().map(ObjectClassEquipment::getId).collect(Collectors.toList()));
			entity.getEquipments().forEach(de -> de.setObjectClass(entity));
			objectClassEquipmentRepository.saveAll(entity.getEquipments());
		}

		return result;
	}

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_WRITE') or hasRole('OBJECT_CLASS_ADMIN')")
	public void delete(ObjectClass entity) {
		log.info("Удаляем класс объктов {}", entity);
		objectClassRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_WRITE') or hasRole('OBJECT_CLASS_ADMIN')")
	public Iterable<ObjectClass> saveAll(Iterable<ObjectClass> entities) {
		log.info("Сохраняем коллекцию классов объктов {}", entities);
		return objectClassRepository.saveAll(entities);
	}

	@Override
	@PreAuthorize("hasAuthority('OBJECT_CLASS_READ') or hasRole('OBJECT_CLASS_ADMIN')")
	public Page<ObjectClass> findByString(String value, Pageable pageable) {
		if (StringUtils.isEmpty(value))
			return findAll(pageable);
		log.info("Извлекаем все классы объктов по фильтру {} постранично {}", value, pageable);
		return objectClassRepository.findByNameContainsIgnoreCase(value, pageable);
	}
}
