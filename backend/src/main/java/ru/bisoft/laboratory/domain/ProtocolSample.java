package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@NamedEntityGraph(name = "protocolSample.byProtocolJoins", attributeNodes = { //
        @NamedAttributeNode(value = "sample")//
})

@Entity
@Table(name = "PROTOCOL_SAMPLE")
@Getter
@Setter
@NoArgsConstructor
public class ProtocolSample extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PROTOCOL_SAMPLE_GEN_ID", sequenceName = "PROTOCOL_SAMPLE_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "PROTOCOL_SAMPLE_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROTOCOL", referencedColumnName = "ID")
    private Protocol protocol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SAMPLE", referencedColumnName = "ID")
    private Sample sample;
}
