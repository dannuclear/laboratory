package ru.bisoft.laboratory.domain;

import lombok.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "SELECTION_EQUIPMENT")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SelectionEquipment extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SELECTION_EQUIPMENT_GEN_ID", sequenceName = "SELECTION_EQUIPMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "SELECTION_EQUIPMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SELECTION", referencedColumnName = "ID")
    private Selection selection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPMENT", referencedColumnName = "ID")
    private Equipment equipment;
}
