package ru.bisoft.laboratory.domain.equipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "EQUIPMENT_MAINTENANCE")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "equipment")
@JsonIgnoreProperties("equipment")
@Builder
@AllArgsConstructor
public class EquipmentMaintenance {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "EQUIPMENT_MAINTENANCE_GEN_ID", sequenceName = "EQUIPMENT_MAINTENANCE_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "EQUIPMENT_MAINTENANCE_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "CREATE_DATE")
    @NotNull
    @JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate date;

    @Column(name = "NUM")
    @NotEmpty
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EQUIPMENT", referencedColumnName = "ID")
    @JsonIgnore
    private Equipment equipment;
}
