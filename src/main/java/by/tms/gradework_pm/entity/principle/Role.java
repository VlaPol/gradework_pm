package by.tms.gradework_pm.entity.principle;

import by.tms.gradework_pm.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();

}
