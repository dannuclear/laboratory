package ru.bisoft.laboratory.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EMPLOYEE_DOCUMENT")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class EmployeeDocument extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "EMPLOYEE_DOCUMENT_GEN_ID", sequenceName = "EMPLOYEE_DOCUMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "EMPLOYEE_DOCUMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENT", referencedColumnName = "ID")
    private Document document;
}
