package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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

	@Column(name = "CODE")
	private String code;

	@Column(name = "REQUEST_DATE")
	private Date requestDate;

	@Column(name = "PURPOSE")
	private String purpose;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "PLACE")
	private String place;

	@Column(name = "EXECUTION_DATE")
	private Date executionDate;

	@Column(name = "ACTIONS")
	private Integer actions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DECLARANT", referencedColumnName = "ID")
	private LegalEntity declarant;
}
