package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.employee.EmployeeDto;
import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    List<EmployeeProjectsCountDto> countEmployeeProjects();

    List<EmployeeDto> getAllEmployees();

    Optional<EmployeeDto> findByEmail(String email);
}
