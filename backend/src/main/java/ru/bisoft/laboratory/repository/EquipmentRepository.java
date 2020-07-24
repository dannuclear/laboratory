package ru.bisoft.laboratory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import java.util.Optional;

@Transactional(readOnly = true)
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @EntityGraph(attributePaths = {"lastVerification", "lastMaintenance"})
    Page<Equipment> findByNameContainsIgnoreCase(@Param("name") String name, Pageable p);

    @Override
    @EntityGraph(attributePaths = {"lastVerification", "lastMaintenance"})
    Page<Equipment> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"lastVerification", "lastMaintenance"})
    Optional<Equipment> findById(Integer id);

    @Query(value = "update equipment set " + //
            "id_equipment_maintenance = (select id from equipment_maintenance order by create_date desc limit 1), " + //
            "id_equipment_verification = (select id from equipment_verification order by create_date desc limit 1) " + //
            "where id = ?1", nativeQuery = true)
    @Transactional(readOnly = false)
    @Modifying
    void updateMaintenanceAndVerification(Equipment equipment);

}
