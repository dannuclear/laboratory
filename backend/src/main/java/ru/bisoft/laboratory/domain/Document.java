package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DOCUMENT")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Document extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "DOCUMENT_GEN_ID", sequenceName = "DOCUMENT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "DOCUMENT_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "HEADER")
	@NotEmpty(message = "Заголовок документа не задан")
	private String header;

	@Column(name = "TITLE")
	private String title;

	@JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy")
	@Column(name = "COMMIT_DATE")
	private LocalDate commitDate;

	@JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy")
	@Column(name = "BEGIN_DATE")
	@NotNull(message = "Дата начала действия документа не установлена")
	private LocalDate beginDate;

	@Column(name = "END_DATE")
	private LocalDate endDate;

//	@OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
	@Transient
	private List<DocumentEquipment> documentEquipments;
}
