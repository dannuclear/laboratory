package ru.bisoft.laboratory.domain.equipment;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "EQUIPMENT_VERIFICATION")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "equipment")
@Builder
@AllArgsConstructor
@JsonIgnoreProperties("equipment")
public class EquipmentVerification {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "EQUIPMENT_VERIFICATION_GEN_ID", sequenceName = "EQUIPMENT_VERIFICATION_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "EQUIPMENT_VERIFICATION_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "CREATE_DATE")
	@JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate date;

	@Column(name = "NUM")
	private String number;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EQUIPMENT", referencedColumnName = "ID")
	private Equipment equipment;
}
