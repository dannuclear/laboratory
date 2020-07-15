package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Protocol;
import ru.bisoft.laboratory.domain.ProtocolSample;

@Transactional(readOnly = true)
public interface ProtocolSampleRepository extends JpaRepository<ProtocolSample, Integer> {
	@EntityGraph(value = "protocolSample.byProtocolJoins")
	Page<ProtocolSample> findByProtocol(Protocol protocol, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByProtocolAndIdNotIn(Protocol protocol, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByProtocol(Protocol protocol);
}
