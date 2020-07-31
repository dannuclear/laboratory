package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee {
    @Id
    @SequenceGenerator(name = "EMPLOYEE_GEN_ID", sequenceName = "EMPLOYEE_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "EMPLOYEE_GEN_ID", strategy = SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "SURNAME", length = 100)
    private String surname;

    @Column(name = "PATRONYMIC", length = 100)
    private String patronymic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ORGANIZATION", referencedColumnName = "ID")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DEPARTMENT", referencedColumnName = "ID")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_POST", referencedColumnName = "ID")
    private Post post;

    @Transient
    private List<EmployeeDocument> employeeDocuments;

    @Transient
    private List<EmployeeEquipment> employeeEquipments;

    @Transient
    private List<EmployeeProperty> employeeProperties;
}
