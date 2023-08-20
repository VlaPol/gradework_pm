package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.employee.EmployeeDto;
import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    EmployeeRepository employeeRepository;

    @Override
    public void createNewEmployee(Employee employee) {
        employeeRepository.create(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public List<EmployeeProjectsCountDto> getEmployeeWithProjectsCount() {
        return employeeRepository.countEmployeeProjects();
    }

    @Override
    public EmployeeDto findByEmail(String email) throws BusinessException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Такого работника не существует"));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.remove(id);
    }

}
