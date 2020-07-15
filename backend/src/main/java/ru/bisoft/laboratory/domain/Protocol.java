package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PROTOCOL")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "samples" })
public class Protocol extends CustomEntity {
	@Id
	@Column(name = "id")
	@SequenceGenerator(name = "PROTOCOL_GEN_ID", sequenceName = "PROTOCOL_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "PROTOCOL_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@Column(name = "NUM", length = 255)
	private String number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SELECTION", referencedColumnName = "ID")
	private Selection selection;

	@Transient
	private List<ProtocolSample> samples;
}
