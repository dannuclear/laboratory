package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.Request;
import ru.bisoft.laboratory.repository.RequestRepository;
import ru.bisoft.laboratory.service.RequestService;

@Log4j2
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('REQUEST_ADMIN')")
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    @PreAuthorize("hasAuthority('REQUEST_WRITE') or hasRole('REQUEST_ADMIN')")
    public Request create() {
        log.info("Создаем заявку");
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('REQUEST_READ') or hasRole('REQUEST_ADMIN')")
    public Request findById(Integer id) {
        log.info("Извлекаем заявку по id {}", id);
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('REQUEST_READ') or hasRole('REQUEST_ADMIN')")
    public Page<Request> findAll(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('REQUEST_WRITE') or hasRole('REQUEST_ADMIN')")
    public Request save(Request entity) {
        return requestRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('REQUEST_WRITE') or hasRole('REQUEST_ADMIN')")
    public void delete(Request entity) {
        requestRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('REQUEST_WRITE') or hasRole('REQUEST_ADMIN')")
    public Iterable<Request> saveAll(Iterable<Request> entities) {
        return requestRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('REQUEST_READ') or hasRole('REQUEST_ADMIN')")
    public Page<Request> findByString(String value, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }
}
