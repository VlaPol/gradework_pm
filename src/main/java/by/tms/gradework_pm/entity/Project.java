package by.tms.gradework_pm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project")
public class Project extends BaseEntity implements Serializable {

    private String name;
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
    @JsonIgnore
    private List<Employee> employees;
}
