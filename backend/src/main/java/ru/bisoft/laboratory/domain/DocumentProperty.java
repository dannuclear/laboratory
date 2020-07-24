package ru.bisoft.laboratory.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "DOCUMENT_PROPERTY")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DocumentProperty extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "DOCUMENT_PROPERTY_GEN_ID", sequenceName = "DOCUMENT_PROPERTY_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "DOCUMENT_PROPERTY_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENT", referencedColumnName = "ID")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROPERTY", referencedColumnName = "ID")
    private Property property;
}
