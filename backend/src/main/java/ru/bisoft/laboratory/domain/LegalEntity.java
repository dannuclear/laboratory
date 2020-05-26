package ru.bisoft.laboratory.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "LEGAL_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LegalEntity {
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "NAME", length = 50)
	private String name;

	@Column(name = "INN", length = 10)
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
