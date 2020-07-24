package ru.bisoft.laboratory.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.bisoft.laboratory.domain.Employee;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

@NamedEntityGraph(name = "user.allJoins", attributeNodes = { //
        @NamedAttributeNode(value = "employee", subgraph = "employeeSub"), //
}, //
        subgraphs = { //
                @NamedSubgraph(name = "employeeSub", attributeNodes = { //
                        @NamedAttributeNode(value = "organization"), //
                        @NamedAttributeNode(value = "department", subgraph = "departmentSub") //
                }), //
                @NamedSubgraph(name = "departmentSub", attributeNodes = { //
                        @NamedAttributeNode(value = "organization"), //
                }), //
        })
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "USERS_GEN_ID", sequenceName = "USERS_GEN_ID", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "USERS_GEN_ID", strategy = SEQUENCE)
    private Integer id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD", updatable = false)
    @JsonIgnore
    private String password;

    @Column(name = "GROUPS")
    private String groups;

    @Column(name = "ROLES")
    @Type(type = "jsonb")
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID")
    @JsonIgnore
    private Employee employee;

    public String getEmployeeFullName() {
        if (employee == null)
            return "Сотрудник не ассоциирован";
        return new StringBuilder().append(this.employee.getSurname() != null ? this.employee.getSurname().toUpperCase().trim() : "")//
                .append(" ")//
                .append(this.employee.getName() != null ? this.employee.getName().toUpperCase().trim() : "")//
                .append(" ")//
                .append(this.employee.getPatronymic() != null ? this.employee.getPatronymic().toUpperCase().trim() : "")//
                .toString()//
                .trim();
    }

    public String getOrganizationName() {
        if (this.employee == null || this.getEmployee().getOrganization() == null)
            return "Организация не ассоциирована с пользователем";
        return this.employee.getOrganization().getName();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> set = new HashSet<GrantedAuthority>();
        if (getGroups() == null)
            return set;
        for (String group : getGroups().split(","))
            set.add(new SimpleGrantedAuthority(group.trim()));
        return set;
    }

    // public boolean isInRole(String... roles) {
    // return getAuthorities().containsAll(Arrays.asList(roles));
    // }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
