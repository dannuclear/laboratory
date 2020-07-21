package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionSample;

@Transactional(readOnly = true)
public interface SelectionSampleRepository extends JpaRepository<SelectionSample, Integer> {
    @EntityGraph(value = "selectionSample.bySelectionJoins")
    Page<SelectionSample> findBySelection(Selection selection, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteBySelection(Selection selection);
}
