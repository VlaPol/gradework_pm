package by.tms.gradework_pm.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project extends BaseEntity implements Serializable {

    @Column(name = "name")
    @NotBlank
    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @Column(name = "description")
    @NotBlank
    @Size(min = 3, max = 400)
    private String description;

    private String stage;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToMany(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(name = "project_emp",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employees;
}
