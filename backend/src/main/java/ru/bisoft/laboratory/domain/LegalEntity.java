package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bisoft.validator.annotation.INNValid;

@Entity
@Table(name = "LEGAL_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LegalEntity {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "LEGAL_ENTITY_GEN_ID", sequenceName = "LEGAL_ENTITY_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "LEGAL_ENTITY_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "INN", length = 10)
	@INNValid
	private String INN;

	/**
	 * Юридический адрес
	 */
	@Column(name = "LEGAL_ADDRESS", length = 500)
	private String legalAddress;

	/**
	 * Юридический адрес
	 */
	@Column(name = "ACTUAL_ADDRESS", length = 500)
	private String actualAddress;
}
