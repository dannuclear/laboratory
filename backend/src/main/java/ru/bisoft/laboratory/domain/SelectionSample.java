package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedEntityGraph(name = "selectionSample.bySelectionJoins", attributeNodes = { //
		@NamedAttributeNode(value = "sample")//
})

@Entity
@Table(name = "SELECTION_SAMPLE")
@Getter
@Setter
@NoArgsConstructor
public class SelectionSample extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SELECTION_SAMPLE_GEN_ID", sequenceName = "SELECTION_SAMPLE_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SELECTION_SAMPLE_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SELECTION", referencedColumnName = "ID")
	private Selection selection;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SAMPLE", referencedColumnName = "ID")
	private Sample sample;
}
