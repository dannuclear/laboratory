package ru.bisoft.laboratory.domain.equipment;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bisoft.laboratory.domain.CustomEntity;
import ru.bisoft.laboratory.domain.DocumentEquipment;
import ru.bisoft.laboratory.domain.EmployeeEquipment;

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
	private String verificationInterval;

	@Column(name = "MAINTENANCE_INTERVAL")
	private String maintenanceInterval;

	@Column(name = "START_USE_DATE")
	private String startUseDate;

	@Column(name = "END_USE_DATE")
	private String endUseDate;

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
