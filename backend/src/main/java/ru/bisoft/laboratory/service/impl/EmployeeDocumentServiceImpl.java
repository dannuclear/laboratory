package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Document;
import ru.bisoft.laboratory.domain.Employee;
import ru.bisoft.laboratory.domain.EmployeeDocument;
import ru.bisoft.laboratory.repository.EmployeeDocumentRepository;
import ru.bisoft.laboratory.service.EmployeeDocumentService;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EMPLOYEE_DOCUMENT_ADMIN')")
public class EmployeeDocumentServiceImpl implements EmployeeDocumentService {

    private final EmployeeDocumentRepository employeeDocumentRepository;

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_WRITE') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public EmployeeDocument create() {
        log.info("Создаем документ оборудования");
        EmployeeDocument employeeDocument = new EmployeeDocument();
        return employeeDocument;
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_READ') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public EmployeeDocument findById(Integer id) {
        log.info("Извлекаем отдел по id {}", id);
        return employeeDocumentRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_READ') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public Page<EmployeeDocument> findAll(Pageable pageable) {
        log.info("Извлекаем все документы оборудования постранично {}", pageable);
        return employeeDocumentRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_WRITE') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public EmployeeDocument save(EmployeeDocument entity) {
        log.info("Сохраняем документ оборудования {}", entity);
        return employeeDocumentRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_WRITE') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public void delete(EmployeeDocument entity) {
        log.info("Удаляем документ оборудования {}", entity);
        employeeDocumentRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_WRITE') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public void deleteByEmployeeAndIdNotIn(Employee employee, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", employee, ids);
        if (ids.size() == 0)
            employeeDocumentRepository.deleteByEmployee(employee);
        else
            employeeDocumentRepository.deleteByEmployeeAndIdNotIn(employee, ids);
    }

    @Override
    public void deleteByDocumentAndIdNotIn(Document document, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} документы с id {}", document, ids);
        if (ids.size() == 0)
            employeeDocumentRepository.deleteByDocument(document);
        else
            employeeDocumentRepository.deleteByDocumentAndIdNotIn(document, ids);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_READ') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public Iterable<EmployeeDocument> saveAll(Iterable<EmployeeDocument> entities) {
        log.info("Сохраняем коллекцию документов оборудования {}", entities);
        return employeeDocumentRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EMPLOYEE_DOCUMENT_READ') or hasRole('EMPLOYEE_DOCUMENT_ADMIN')")
    public Page<EmployeeDocument> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<EmployeeDocument> findByDocument(Document document, Pageable pageable) {
        log.info("Извлекаем всех сотрудников по оборудованию {} постранично {}", document, pageable);
        Page<EmployeeDocument> result = employeeDocumentRepository.findByDocument(document, pageable);
        result.getContent().forEach(de -> {
            de.setDocument(null);
            de.getEmployee().setEmployeeDocuments(null);
        });
        return result;
    }

    @Override
    public Page<EmployeeDocument> findByEmployee(Employee employee, Pageable pageable) {
        log.info("Извлекаем все документы оборудования по документу {} постранично {}", employee, pageable);
        Page<EmployeeDocument> result = employeeDocumentRepository.findByEmployee(employee, pageable);
        result.getContent().forEach(de -> {
            de.setEmployee(null);
            de.getDocument().setEmployeeDocuments(null);
        });
        return result;
    }
}
