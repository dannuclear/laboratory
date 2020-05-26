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

//@NamedEntityGraph(name = "request.allJoins", attributeNodes = { //
//		@NamedAttributeNode(value = "unit"), //
//		@NamedAttributeNode(value = "requestType"), //
//})

/**
 * @author Denis Показатель
 */
@Entity
@Table(name = "REQUEST")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Request extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "REQUEST_GEN_ID", sequenceName = "REQUEST_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "REQUEST_GEN_ID", strategy = SEQUENCE)
	private Integer id;

//	@Column(name = "CODE")
//	@NotEmpty(message = "Наименование параметра должно быть задано")
//	private String code;
}
