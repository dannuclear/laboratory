package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;

@Transactional(readOnly = true)
public interface SamplePropertyRepository extends JpaRepository<SampleProperty, Integer> {
	@EntityGraph(value = "sampleProperty.bySampleJoins")
	Page<SampleProperty> findBySample(Sample sample, Pageable p);
}
