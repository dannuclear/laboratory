package ru.bisoft.laboratory.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "OBJECT_CLASS_EQUIPMENT")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"equipment", "objectClass"})
public class ObjectClassEquipment {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "OBJECT_CLASS_EQUIPMENT_GEN_ID", sequenceName = "OBJECT_CLASS_EQUIPMENT_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "OBJECT_CLASS_EQUIPMENT_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPMENT", referencedColumnName = "ID")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_OBJECT_CLASS", referencedColumnName = "ID")
    private ObjectClass objectClass;
}
