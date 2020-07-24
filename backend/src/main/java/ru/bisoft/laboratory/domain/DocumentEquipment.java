package ru.bisoft.laboratory.domain;

import lombok.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "DOCUMENT_EQUIPMENT")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DocumentEquipment extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "DOCUMENT_EQUIPMENT_GEN_ID", sequenceName = "DOCUMENT_EQUIPMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "DOCUMENT_EQUIPMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_DOCUMENT", referencedColumnName = "ID")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPMENT", referencedColumnName = "ID")
    private Equipment equipment;
}
