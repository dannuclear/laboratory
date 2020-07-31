package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.Selection;
import ru.bisoft.laboratory.domain.SelectionEmployee;

public interface SelectionEmployeeService extends GuideService<SelectionEmployee> {
    Page<SelectionEmployee> findBySelection(Selection selection, Pageable pageable);

    Page<SelectionEmployee> findByEmployee(Employee employee, Pageable pageable);

    void deleteBySelectionAndIdNotIn(Selection selection, Iterable<Integer> ids);

    void deleteBySelection(Selection selection);
}
