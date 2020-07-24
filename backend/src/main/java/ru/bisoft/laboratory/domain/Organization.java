package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ORGANIZATION")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Organization {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "FULL_NAME", length = 200)
    private String fullName;

    @Column(name = "INN", length = 10)
    private String INN;

    @Column(name = "OGRN", length = 13)
    private String OGRN;

    @Column(name = "ADDRESS", length = 200)
    private String address;

    @Column(name = "EMAIL", length = 100)
    private String eMail;

    @Column(name = "PHONE", length = 50)
    private String phone;

    @Transient
    private List<Department> departments;
}
