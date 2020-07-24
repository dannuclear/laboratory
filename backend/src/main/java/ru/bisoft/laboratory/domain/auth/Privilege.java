package ru.bisoft.laboratory.domain.auth;

public enum Privilege {
    READ("Чтение"), WRITE("Запись");

    private String label;

    Privilege(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
