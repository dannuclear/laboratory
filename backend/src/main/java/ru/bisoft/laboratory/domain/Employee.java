package ru.bisoft.laboratory.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "SURNAME", length = 100)
	private String surname;

	@Column(name = "PATRONYMIC", length = 100)
	private String patronymic;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ORGANIZATION", referencedColumnName = "ID")
	private Organization organization;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DEPARTMENT", referencedColumnName = "ID")
	private Department department;
}
