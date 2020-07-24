package ru.bisoft.laboratory.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "SELECTION_EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SelectionEmployee extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SELECTION_EMPLOYEE_GEN_ID", sequenceName = "SELECTION_EMPLOYEE_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "SELECTION_EMPLOYEE_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SELECTION", referencedColumnName = "ID")
    private Selection selection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
    private Employee employee;
}
