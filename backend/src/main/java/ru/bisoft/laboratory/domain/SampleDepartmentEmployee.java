package ru.bisoft.laboratory.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "SAMPLE_DEPARTMENT_EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SampleDepartmentEmployee extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SAMPLE_DEPARTMENT_EMPLOYEE_GEN_ID", sequenceName = "SAMPLE_DEPARTMENT_EMPLOYEE_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "SAMPLE_DEPARTMENT_EMPLOYEE_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SAMPLE_DEPARTMENT", referencedColumnName = "ID")
    private SampleDepartment sampleDepartment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
    private Employee employee;
}
