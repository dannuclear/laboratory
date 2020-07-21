package ru.bisoft.laboratory.domain;

import javax.persistence.Transient;

public class CustomEntity {
    @Transient
    private Status entityStatus = Status.ACTIVE;

    public boolean inStatus(Status... statuses) {
        for (Status status : statuses)
            if (status.equals(this.entityStatus))
                return true;
        return false;
    }

    public Status getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(Status entityStatus) {
        this.entityStatus = entityStatus;
    }


    public enum Status {
        ACTIVE("Активен"), MODIFIED("Изменен"), DELETED("Удален");

        private String label;

        Status(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}
