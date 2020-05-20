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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bisoft.laboratory.domain.equipment.Equipment;

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
