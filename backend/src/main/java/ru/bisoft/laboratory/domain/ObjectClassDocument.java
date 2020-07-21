package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "OBJECT_CLASS_DOCUMENT")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"document", "objectClass"})
public class ObjectClassDocument {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "OBJECT_CLASS_DOCUMENT_GEN_ID", sequenceName = "OBJECT_CLASS_DOCUMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "OBJECT_CLASS_DOCUMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENT", referencedColumnName = "ID")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OBJECT_CLASS", referencedColumnName = "ID")
    private ObjectClass objectClass;

}
