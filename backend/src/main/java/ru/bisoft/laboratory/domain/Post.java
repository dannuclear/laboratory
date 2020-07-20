package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "POST")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Post extends CustomEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "POST_GEN_ID", sequenceName = "POST_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "POST_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NAME")
    @NotEmpty(message = "Наименование должно быть задано")
    private String name;
}
