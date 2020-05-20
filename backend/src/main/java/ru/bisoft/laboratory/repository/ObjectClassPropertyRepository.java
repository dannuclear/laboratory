package ru.bisoft.laboratory.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.laboratory.domain.ObjectClass;
import ru.bisoft.laboratory.domain.ObjectClassProperty;

@Transactional(readOnly = true)
public interface ObjectClassPropertyRepository extends JpaRepository<ObjectClassProperty, Integer> {

	@EntityGraph(attributePaths = { "property.propertyType", "property.unit" })
	Iterable<ObjectClassProperty> findByObjectClass(ObjectClass objectClass);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByObjectClassAndIdNotIn(ObjectClass objectClass, Iterable<Integer> ids);

	@Modifying
	@Transactional(readOnly = false)
	void deleteByObjectClass(ObjectClass objectClass);
}
