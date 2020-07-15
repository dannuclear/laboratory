package ru.bisoft.laboratory.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;

@Transactional(readOnly = true)
public interface SamplePropertyRepository extends JpaRepository<SampleProperty, Integer> {
	@EntityGraph(value = "sampleProperty.bySampleJoins")
	Page<SampleProperty> findBySample(Sample sample, Pageable p);

	@Modifying
	@Transactional(readOnly = false)
	void deleteBySampleAndIdNotIn(Sample sample, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteBySample(Sample sample);

	@Override
	@EntityGraph(value = "sampleProperty.fullJoins")
	Optional<SampleProperty> findById(Integer id);
}
