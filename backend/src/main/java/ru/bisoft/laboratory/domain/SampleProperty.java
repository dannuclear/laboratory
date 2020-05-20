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
import javax.persistence.NamedSubgraph;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NamedEntityGraph(name = "sampleProperty.bySampleJoins", attributeNodes = { //
		@NamedAttributeNode(value = "property", subgraph = "propertySub"), //
}, //
		subgraphs = { //
				@NamedSubgraph(name = "propertySub", attributeNodes = { //
						@NamedAttributeNode(value = "propertyType"), //
						@NamedAttributeNode(value = "unit")//
				}) //
		})

@Entity
@Table(name = "SAMPLE_PROPERTY")
@Getter
@Setter
@NoArgsConstructor
public class SampleProperty extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SAMPLE_PROPERTY_GEN_ID", sequenceName = "SAMPLE_PROPERTY_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SAMPLE_PROPERTY_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROPERTY", referencedColumnName = "ID")
	private Property property;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SAMPLE", referencedColumnName = "ID")
	private Sample sample;
}
