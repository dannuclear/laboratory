package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EXPERTISE")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Expertise extends CustomEntity{
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "EXPERTISE_GEN_ID", sequenceName = "EXPERTISE_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "EXPERTISE_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "NAME", length = 500)
	private String name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROTOCOL", referencedColumnName = "ID")
	private Protocol protocol;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
	private Employee employee;
}
