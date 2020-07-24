package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeDocument;

import java.util.Collection;

public interface EmployeeDocumentService extends GuideService<EmployeeDocument> {
    Page<EmployeeDocument> findByEmployee(Employee employee, Pageable pageable);

    Page<EmployeeDocument> findByDocument(Document document, Pageable pageable);

    void deleteByDocumentAndIdNotIn(Document document, Collection<Integer> ids);

    void deleteByEmployeeAndIdNotIn(Employee employee, Collection<Integer> ids);
}
