package ru.bisoft.laboratory.domain.auth;

public enum Privilege {
	READ("Чтение"), WRITE("Запись");
	
	private String label;

	private Privilege(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
