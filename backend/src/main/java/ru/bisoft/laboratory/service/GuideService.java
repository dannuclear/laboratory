package ru.bisoft.laboratory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GuideService<T> {

    T create();

    T findById(Integer id);

    Page<T> findAll(Pageable pageable);

    Page<T> findByString(String value, Pageable pageable);

    T save(T entity);

    Iterable<T> saveAll(Iterable<T> entities);

    void delete(T entity);
}
