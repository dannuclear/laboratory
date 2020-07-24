package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.SEQUENCE;

@NamedEntityGraphs(value = {@NamedEntityGraph(name = "sampleProperty.bySampleJoins", attributeNodes = { //
        @NamedAttributeNode(value = "property", subgraph = "propertySub"), //
}, //
        subgraphs = { //
                @NamedSubgraph(name = "propertySub", attributeNodes = { //
                        @NamedAttributeNode(value = "propertyType"), //
                        @NamedAttributeNode(value = "unit")//
                }) //
        }), @NamedEntityGraph(name = "sampleProperty.fullJoins", attributeNodes = { //
        @NamedAttributeNode(value = "property", subgraph = "propertySub"), //
        @NamedAttributeNode(value = "sample") //
}, //
        subgraphs = { //
                @NamedSubgraph(name = "propertySub", attributeNodes = { //
                        @NamedAttributeNode(value = "propertyType"), //
                        @NamedAttributeNode(value = "unit")//
                }) //
        })})

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

    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROPERTY", referencedColumnName = "ID")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SAMPLE", referencedColumnName = "ID")
    private Sample sample;
}
