package ru.bisoft.laboratory.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bisoft.laboratory.domain.equipment.Equipment;
import ru.bisoft.laboratory.domain.equipment.EquipmentMaintenance;
import ru.bisoft.laboratory.repository.EquipmentMaintenanceRepository;
import ru.bisoft.laboratory.service.EquipmentMaintenanceService;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('EQUIPMENT_MAINTENANCE_ADMIN')")
public class EquipmentMaintenanceServiceImpl implements EquipmentMaintenanceService {

    private final EquipmentMaintenanceRepository equipmentMaintenanceRepository;

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_WRITE') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public EquipmentMaintenance create() {
        log.info("Создаем т.о. оборудования");
        EquipmentMaintenance equipmentMaintenance = new EquipmentMaintenance();
        return equipmentMaintenance;
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_READ') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public EquipmentMaintenance findById(Integer id) {
        log.info("Извлекаем тех. обслуживание по id {}", id);
        return equipmentMaintenanceRepository.findById(id).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_READ') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public Page<EquipmentMaintenance> findAll(Pageable pageable) {
        log.info("Извлекаем все т.о оборудования постранично {}", pageable);
        return equipmentMaintenanceRepository.findAll(pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_WRITE') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public EquipmentMaintenance save(EquipmentMaintenance entity) {
        log.info("Сохраняем т.о. оборудования {}", entity);
        return equipmentMaintenanceRepository.save(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_WRITE') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public void delete(EquipmentMaintenance entity) {
        log.info("Удаляем т.о. оборудования {}", entity);
        equipmentMaintenanceRepository.delete(entity);
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_READ') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public Iterable<EquipmentMaintenance> saveAll(Iterable<EquipmentMaintenance> entities) {
        log.info("Сохраняем коллекцию т.о. оборудования {}", entities);
        return equipmentMaintenanceRepository.saveAll(entities);
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_READ') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public Page<EquipmentMaintenance> findByString(String value, Pageable pageable) {
        throw new UnsupportedOperationException("Неподдерживаемая опрация");
    }

    @Override
    public Page<EquipmentMaintenance> findByEquipment(Equipment equipment, Pageable pageable) {
        log.info("Извлекаем все т.о. оборудования {} постранично {}", equipment, pageable);
        Page<EquipmentMaintenance> result = equipmentMaintenanceRepository.findByEquipment(equipment, pageable);
        result.getContent().forEach(sp -> sp.setEquipment(null));
        return result;
    }

    @Override
    @PreAuthorize("hasAuthority('EQUIPMENT_MAINTENANCE_WRITE') or hasRole('EQUIPMENT_MAINTENANCE_ADMIN')")
    public void deleteByEquipmentAndIdNotIn(Equipment equipment, Collection<Integer> ids) {
        if (ids == null)
            throw new IllegalArgumentException("ids не может быть пустым");
        log.info("Удаляем у оборудования {} техническое обслуживание с id {}", equipment, ids);
        if (ids.size() == 0)
            equipmentMaintenanceRepository.deleteByEquipment(equipment);
        else
            equipmentMaintenanceRepository.deleteByEquipmentAndIdNotIn(equipment, ids);
    }
}
