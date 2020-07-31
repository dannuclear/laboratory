package ru.bisoft.laboratory.domain.equipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bisoft.laboratory.domain.CustomEntity;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.EmployeeEquipment;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EQUIPMENT")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "documentEquipments")
public class Equipment extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "EQUIPMENT_GEN_ID", sequenceName = "EQUIPMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "EQUIPMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NAME")
    @NotEmpty(message = "Наименование оборудования не может быть пустым")
    private String name;

    @Column(name = "PRODUCER")
    private String producer;

    @Column(name = "SERIAL_NUMBER")
    @NotEmpty(message = "Серийный номер оборудования не может быть пустым")
    private String serialNumber;

    @Column(name = "INVENTORY_NUMBER")
    private String inventoryNumber;

    @Column(name = "COMMISSIONING_YEAR")
    private Integer commissioningYear;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CHARACTERISTICS")
    private String characteristics;

    @Column(name = "VERIFICATION_INTERVAL")
    private Integer verificationInterval;

    @Column(name = "MAINTENANCE_INTERVAL")
    private Integer maintenanceInterval;

    @Column(name = "START_USE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date startUseDate;

    @Column(name = "END_USE_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date endUseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPMENT_VERIFICATION", referencedColumnName = "ID", updatable = false, insertable = false)
    private EquipmentVerification lastVerification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPMENT_MAINTENANCE", referencedColumnName = "ID", updatable = false, insertable = false)
    private EquipmentMaintenance lastMaintenance;

    @Transient
    private List<DocumentEquipment> documentEquipments;

    @Transient
    private List<EmployeeEquipment> employeeEquipments;

    @Transient
    private List<EquipmentMaintenance> maintenanceList;

    @Transient
    private List<EquipmentVerification> verificationList;
}
