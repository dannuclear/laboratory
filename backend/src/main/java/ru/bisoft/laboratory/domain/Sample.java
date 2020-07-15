package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SAMPLE")
@Getter
@Setter
@NoArgsConstructor
public class Sample extends CustomEntity {
	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "SAMPLE_GEN_ID", sequenceName = "SAMPLE_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "SAMPLE_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	/**
	 * Код образца/пробы
	 */
	@Column(name = "CODE")
	private String code;

	/**
	 * Место отбора пробы
	 */
	@Column(name = "SELECTION_POINT")
	private String selectionPoint;

	/**
	 * Дата и время отбора пробы
	 */
	@JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
	@Column(name = "SELECTION_TIMESTAMP")
	@NotNull(message = "Не указана дата и время отбора")
	private LocalDateTime selectionTimestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_OBJECT_CLASS", referencedColumnName = "ID")
	@JsonIgnore
	private ObjectClass objectClass;
	
	public Integer getObjectClassId() {
		if (objectClass != null)
			return objectClass.getId();
		return null;
	}

	public void setObjectClassId(Integer parentId) {
		if (objectClass != null)
			objectClass.setId(parentId);
		else if (parentId != null) {
			objectClass = new ObjectClass();
			objectClass.setId(parentId);
		} else
			objectClass = null;
	}
	
	@Transient
	private List<SampleProperty> properties;
}
