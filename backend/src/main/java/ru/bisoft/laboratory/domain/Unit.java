package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.util.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "UNIT")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Unit extends CustomEntity {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "UNIT_GEN_ID", sequenceName = "UNIT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "UNIT_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "NAME")
	@NotEmpty(message = "Наименование должно быть задано")
	private String name;

	@Column(name = "SHORT_NAME")
	@NotEmpty(message = "Сокращенное название должно быть задано")
	private String shortName;

	@Column(name = "EXPRESSION")
	private String expression;

	public String getExpression() {
		if (StringUtils.isEmpty(this.expression))
			this.expression = shortName;
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}
