package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends BaseRepository<Employee, Long>{
    List<EmployeeProjectsCountDto> countEmployeeProjects();
}
