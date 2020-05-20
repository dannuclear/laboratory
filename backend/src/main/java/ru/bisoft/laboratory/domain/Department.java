package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DEPARTMENT")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Department extends CustomEntity{
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "DEPARTMENT_GEN_ID", sequenceName = "DEPARTMENT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "DEPARTMENT_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "NAME", length = 255)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ORGANIZATION", referencedColumnName = "ID")
	private Organization organization;
}
