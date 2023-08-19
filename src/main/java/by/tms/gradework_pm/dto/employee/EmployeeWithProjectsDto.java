package by.tms.gradework_pm.dto.employee;

import by.tms.gradework_pm.entity.Project;
import lombok.Value;

import java.util.List;

@Value
public class EmployeeWithProjectsDto {
    String firstName;
    String lastName;
    List<Project> projects;
}
