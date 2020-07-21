package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.ObjectClass;

@Transactional(readOnly = true)
public interface ObjectClassRepository extends JpaRepository<ObjectClass, Integer> {
    Page<ObjectClass> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

    Page<ObjectClass> findByParentIsNull(Pageable p);
}
