package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@NamedEntityGraph(name = "property.allJoins", attributeNodes = { //
        @NamedAttributeNode(value = "unit"), //
        @NamedAttributeNode(value = "propertyType"), //
})

/**
 * @author Denis Показатель
 */
@Entity
@Table(name = "PROPERTY")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Property extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PROPERTY_GEN_ID", sequenceName = "PROPERTY_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "PROPERTY_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NAME")
    @NotEmpty(message = "Наименование параметра должно быть задано")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_UNIT", referencedColumnName = "ID")
    @NotNull(message = "Единица измерения параметра должна быть задана")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PROPERTY_TYPE", referencedColumnName = "ID")
    @NotNull(message = "Тип параметра должен быть задан")
    private PropertyType propertyType;

    @Transient
    private List<DocumentProperty> documentProperties;

    @Transient
    private List<EmployeeProperty> employeeProperties;

    @Transient
    private List<SampleProperty> sampleProperties;
}
