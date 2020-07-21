package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bisoft.validator.annotation.INNValid;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "LEGAL_ENTITY")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LegalEntity {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "LEGAL_ENTITY_GEN_ID", sequenceName = "LEGAL_ENTITY_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "LEGAL_ENTITY_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "INN", length = 10)
    @INNValid
    private String INN;

    @Column(name = "PHONE", length = 20)
    private String phone;

    /**
     * Юридический адрес
     */
    @Column(name = "LEGAL_ADDRESS", length = 500)
    private String legalAddress;

    /**
     * Фактический адрес
     */
    @Column(name = "FACTUAL_ADDRESS", length = 500)
    private String factualAddress;
}
