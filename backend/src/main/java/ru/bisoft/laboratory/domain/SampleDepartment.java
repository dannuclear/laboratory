package ru.bisoft.laboratory.domain;

import lombok.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "SAMPLE_DEPARTMENT")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SampleDepartment extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SAMPLE_DEPARTMENT_GEN_ID", sequenceName = "SAMPLE_DEPARTMENT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SAMPLE_DEPARTMENT_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SAMPLE", referencedColumnName = "ID")
	private Sample sample;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DEPARTMENT", referencedColumnName = "ID")
	private Department department;

}
