package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public void createNewEmployee(Employee employee) {
        employeeRepository.create(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public List<EmployeeProjectsCountDto> getEmployeeWithProjectsCount() {
        return employeeRepository.countEmployeeProjects();
    }

    @Override
    public Employee findByEmail(String email) throws BusinessException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("No such user"));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.remove(id);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeRepository.update(employee);
    }

}
