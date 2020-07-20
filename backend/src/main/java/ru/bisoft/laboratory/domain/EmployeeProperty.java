package ru.bisoft.laboratory.domain;

import lombok.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EMPLOYEE_PROPERTY")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeProperty extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "EMPLOYEE_PROPERTY_GEN_ID", sequenceName = "EMPLOYEE_PROPERTY_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "EMPLOYEE_PROPERTY_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROPERTY", referencedColumnName = "ID")
	private Property property;
}
