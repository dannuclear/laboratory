package ru.bisoft.laboratory.domain;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "REPORT")
@Getter
@Setter
@NoArgsConstructor
public class Report {

	@Id
	@Column(name = "KEY_REPORT")
	@SequenceGenerator(name = "REPORT_GEN_ID", sequenceName = "REPORT_GEN_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(generator = "REPORT_GEN_ID", strategy = SEQUENCE)
	private int id;

	@Column(name = "NAME_REPORT")
	private String name;

	@Column(name = "DESCRIPTION_REPORT")
	private String description;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "DATA_REPORT")
	private byte[] data;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "org.hibernate.type.BinaryType")
	@Column(name = "SOURCE_REPORT")
	private byte[] source;

	@Column(name = "FORMATS_REPORT")
	private Integer formats;

	@Column(name = "UUID_REPORT")
	private UUID uuid;

	@Column(name = "UPDATE_TS")
	private LocalDateTime updateTimestamp;

	@Column(name = "CREATE_TS")
	private LocalDateTime createTimestamp;

	@Override
	public String toString() {
		return "Отчет [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@JsonIgnore
	public List<Format> getFormatsArray() {
		List<Format> fs = new ArrayList<>();
		this.formats = this.formats == null ? 0 : this.formats;
		for (Format f : Format.values()) {
			if ((this.formats & (1 << f.ordinal())) > 0)
				fs.add(f);
		}
		return fs;
	}

	public void setFormatsArray(List<String> formats) {
		this.formats = 0;
		for (String f : formats) {
			setFormat(Format.valueOf(f));
		}
	}

	public void setFormat(Format reportType) {
		this.formats = this.formats == null ? 0 : this.formats;
		this.formats |= (1 << reportType.ordinal());
	}

	public void clearFormat(Format reportType) {
		this.formats = this.formats == null ? 0 : this.formats;
		this.formats &= ~(1 << reportType.ordinal());
	}

	public static enum Format {
		HTML("HTML"), PDF("PDF"), DOC("Word"), EXCEL("Excel"), XML("XML"), DBF("DBF"), RTF("RTF"), ODT("ODT");

		private String label;

		private Format(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
	}

	public boolean isCompiled() {
		return this.data != null && this.data.length > 0;
	}
	
	public boolean hasSource() {
		return this.source != null && this.source.length > 0;
	}
}
