package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "CONTROL_TYPE")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ControlType extends CustomEntity{
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "CONTROL_TYPE_GEN_ID", sequenceName = "CONTROL_TYPE_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "CONTROL_TYPE_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "NAME", length = 200)
	private String name;

	@Column(name = "ABBREVIATION", length = 20)
	private String abbreviation;

}
