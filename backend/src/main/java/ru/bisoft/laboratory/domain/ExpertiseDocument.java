package ru.bisoft.laboratory.domain;

import lombok.*;
import ru.bisoft.laboratory.domain.equipment.Equipment;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EXPERTISE_DOCUMENT")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ExpertiseDocument extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "EXPERTISE_DOCUMENT_GEN_ID", sequenceName = "EXPERTISE_DOCUMENT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "EXPERTISE_DOCUMENT_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EXPERTISE", referencedColumnName = "ID")
	private Expertise expertise;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DOCUMENT", referencedColumnName = "ID")
	private Document document;
}
