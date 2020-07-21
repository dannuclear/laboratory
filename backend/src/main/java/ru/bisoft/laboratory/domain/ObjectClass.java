package ru.bisoft.laboratory.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "OBJECT_CLASS")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"parent", "childs"})
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
