package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeDocument;

@Transactional(readOnly = true)
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Integer> {
    @EntityGraph(attributePaths = {"employee"})
    Page<EmployeeDocument> findByDocument(Document document, Pageable p);

    @EntityGraph(attributePaths = {"document"})
    Page<EmployeeDocument> findByEmployee(Employee employee, Pageable p);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEmployeeAndIdNotIn(Employee employee, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByEmployee(Employee employee);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByDocumentAndIdNotIn(Document document, Iterable<Integer> ids);

    @Modifying
    @Transactional(readOnly = false)
    void deleteByDocument(Document document);
}
