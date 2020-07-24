package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Denis Вид показателя
 */
@Entity
@Table(name = "PROPERTY_TYPE")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class PropertyType extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PROPERTY_TYPE_GEN_ID", sequenceName = "PROPERTY_TYPE_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "PROPERTY_TYPE_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FULL_NAME")
    private String fullName;
}
