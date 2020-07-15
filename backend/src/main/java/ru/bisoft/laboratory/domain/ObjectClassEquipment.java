package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bisoft.laboratory.domain.equipment.Equipment;

@Entity
@Table(name = "OBJECT_CLASS_EQUIPMENT")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "equipment", "objectClass" })
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
