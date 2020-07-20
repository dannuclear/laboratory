package ru.bisoft.laboratory.domain;

import lombok.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EMPLOYEE_EQUIPMENT")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeEquipment extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "EMPLOYEE_EQUIPMENT_GEN_ID", sequenceName = "EMPLOYEE_EQUIPMENT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "EMPLOYEE_EQUIPMENT_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EQUIPMENT", referencedColumnName = "ID")
	private Equipment equipment;
}
