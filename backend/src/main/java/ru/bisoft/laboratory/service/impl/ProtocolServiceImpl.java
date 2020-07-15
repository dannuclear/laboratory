package ru.bisoft.laboratory.service.impl;

import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.bisoft.laboratory.domain.Protocol;
import ru.bisoft.laboratory.domain.ProtocolSample;
import ru.bisoft.laboratory.repository.ProtocolRepository;
import ru.bisoft.laboratory.repository.ProtocolSampleRepository;
import ru.bisoft.laboratory.service.ProtocolService;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('PROTOCOL_ADMIN')")
public class ProtocolServiceImpl implements ProtocolService {

	private final ProtocolRepository departmentRepository;
	private final ProtocolSampleRepository protocolSampleRepository;

	@Override
	@PreAuthorize("hasAuthority('PROTOCOL_WRITE') or hasRole('PROTOCOL_ADMIN')")
	public Protocol create() {
		log.info("Создаем протокол");
		Protocol department = new Protocol();
		return department;
	}

	@Override
	@PreAuthorize("hasAuthority('PROTOCOL_READ') or hasRole('PROTOCOL_ADMIN')")
	public Protocol findById(Integer id) {
		log.info("Извлекаем протокол по id {}", id);
		return departmentRepository.findById(id).orElse(null);
	}

	@Override
	@PreAuthorize("hasAuthority('PROTOCOL_READ') or hasRole('PROTOCOL_ADMIN')")
	public Page<Protocol> findAll(Pageable pageable) {
		log.info("Извлекаем все протоколы постранично {}", pageable);
		return departmentRepository.findAll(pageable);
	}

	@Override
	@PreAuthorize("hasAuthority('PROTOCOL_WRITE') or hasRole('PROTOCOL_ADMIN')")
	public Protocol save(Protocol entity) {
		log.info("Сохраняем протокол {}", entity);
		Protocol result = departmentRepository.save(entity);

		// Сохраняем пробы по акту отбора
		if (entity.getSamples() != null) {
			if (entity.getSamples().size() == 0)
				protocolSampleRepository.deleteByProtocol(entity);
			else
				protocolSampleRepository.deleteByProtocolAndIdNotIn(entity, entity.getSamples().stream().map(ProtocolSample::getId).collect(Collectors.toList()));
			entity.getSamples().forEach(de -> de.setProtocol(entity));
			protocolSampleRepository.saveAll(entity.getSamples());
		}
		return result;
	}

	@Override
	@PreAuthorize("hasAuthority('PROTOCOL_WRITE') or hasRole('PROTOCOL_ADMIN')")
	public void delete(Protocol entity) {
		log.info("Удаляем протокол {}", entity);
		departmentRepository.delete(entity);
	}

	@Override
	@PreAuthorize("hasAuthority('PROTOCOL_WRITE') or hasRole('PROTOCOL_ADMIN')")
	public Iterable<Protocol> saveAll(Iterable<Protocol> entities) {
		log.info("Сохраняем коллекцию протоколов {}", entities);
		return departmentRepository.saveAll(entities);
	}

	@Override
	public Page<Protocol> findByString(String value, Pageable pageable) {
		return null;
	}
}
