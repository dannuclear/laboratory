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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "OBJECT_CLASS")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = { "parent", "childs" })
public class ObjectClass extends CustomEntity {

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "OBJECT_CLASS_GEN_ID", sequenceName = "OBJECT_CLASS_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "OBJECT_CLASS_GEN_ID", strategy = SEQUENCE)
	private Integer id;

	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ObjectClass> childs;

	@Transient
	private List<ObjectClassDocument> documents;

	@Transient
	private List<ObjectClassProperty> properties;

	@Transient
	private List<ObjectClassEquipment> equipments;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PARENT", referencedColumnName = "ID")
	@JsonIgnore
	private ObjectClass parent;

	@Column(name = "NAME")
	private String name;

	@Column(name = "OKPD_CODE")
	private String okpdCode;

	@Column(name = "TNVED_CODE")
	private String tnvedCode;

	public Integer getParentId() {
		if (parent != null)
			return parent.getId();
		return null;
	}

	public void setParentId(Integer parentId) {
		if (parent != null)
			parent.setId(parentId);
		else if (parentId != null) {
			parent = new ObjectClass();
			parent.setId(parentId);
		} else
			parent = null;
	}
}
