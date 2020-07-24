package ru.bisoft.laboratory.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Denis Отбор
 */
@Entity
@Table(name = "SELECTION")
@ToString(exclude = {"samples"})
@Getter
@Setter
@NoArgsConstructor
public class Selection extends CustomEntity {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SELECTION_GEN_ID", sequenceName = "SELECTION_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "SELECTION_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "CODE")
    @NotEmpty(message = "Код отбора должно быть задано")
    private String code;

    /**
     * Дата и время отбора
     */
    @JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "SELECTION_TS")
    @NotNull(message = "Не указана дата и время отбора")
    private LocalDateTime selectionTimestamp;

    /**
     * Дата и время доставки в ИЛЦ
     */
    @JsonFormat(shape = Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    @Column(name = "DELIVERY_TS")
    @NotNull(message = "Не указана дата и время доставки в ИЛЦ")
    private LocalDateTime deliveryTimestamp;

    @Column(name = "PLACE")
    private String place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_REQUEST", referencedColumnName = "ID")
    private Request request;

    @Transient
    private List<SelectionSample> samples;
}
