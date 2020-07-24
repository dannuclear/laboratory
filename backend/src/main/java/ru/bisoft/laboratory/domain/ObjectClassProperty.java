package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "OBJECT_CLASS_PROPERTY")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"property", "objectClass"})
public class ObjectClassProperty {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "OBJECT_CLASS_PROPERTY_GEN_ID", sequenceName = "OBJECT_CLASS_PROPERTY_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "OBJECT_CLASS_PROPERTY_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROPERTY", referencedColumnName = "ID")
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OBJECT_CLASS", referencedColumnName = "ID")
    private ObjectClass objectClass;

}
