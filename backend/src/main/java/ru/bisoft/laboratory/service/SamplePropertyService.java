package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Sample;
import ru.bisoft.laboratory.domain.SampleProperty;

public interface SamplePropertyService extends GuideService<SampleProperty> {

    Page<SampleProperty> findBySample(Sample sample, Pageable pageable);

    void deleteBySampleAndIdNotIn(Sample sample, Iterable<Integer> ids);

    void deleteBySample(Sample sample);
}
