package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "DEPARTMENT")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Department extends CustomEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "DEPARTMENT_GEN_ID", sequenceName = "DEPARTMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "DEPARTMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NAME", length = 255)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZATION", referencedColumnName = "ID")
    private Organization organization;
}
