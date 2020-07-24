package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "PROTOCOL")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"samples"})
public class Protocol extends CustomEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "PROTOCOL_GEN_ID", sequenceName = "PROTOCOL_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "PROTOCOL_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NUM", length = 255)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SELECTION", referencedColumnName = "ID")
    private Selection selection;

    @Transient
    private List<ProtocolSample> samples;
}
