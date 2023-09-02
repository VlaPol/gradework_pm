package by.tms.gradework_pm.entity;

import by.tms.gradework_pm.util.validation.UniqueValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity implements Serializable {

    @Column(name = "first_name")
    @NotBlank()
    @NotNull()
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank()
    @NotNull()
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(name = "email")
    @NotBlank
    @UniqueValue
    @Email
    private String email;

    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "project_emp",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;
}
