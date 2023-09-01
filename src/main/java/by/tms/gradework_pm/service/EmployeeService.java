package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.exception.BusinessException;

import java.util.List;

public interface EmployeeService {

    void createNewEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<EmployeeProjectsCountDto> getEmployeeWithProjectsCount();


    Employee findByEmail(String email) throws BusinessException;

    void deleteEmployee(Long id);

    void updateEmployee(Employee employee);
}
