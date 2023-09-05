package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    EmployeeRepository employeeRepository;
    EmployeeService employeeService;

    public static final List<Project> projects = null;
    public static final Employee FIRST_EMP = new Employee("husband", "story", "firstemail.mail.com", projects);
    public static final Employee SECOND_EMP = new Employee("case", "per", "secondmail.mail.com", projects);
    public static final Employee THIRD_EMP = new Employee("John", "Doe", "testemail@mail.com", projects);


    @BeforeEach
    void setRepository() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    void shouldCreateNewEmployee() {

        employeeService.createNewEmployee(FIRST_EMP);
        verify(employeeRepository).create(FIRST_EMP);
    }

    @Test
    void shouldReturnListOfAllEmployees() {

        List<Employee> employees = new ArrayList<>();
        employees.add(FIRST_EMP);
        employees.add(SECOND_EMP);
        employees.add(THIRD_EMP);
        when(employeeRepository.getAllEmployees())
                .thenReturn(employees);

        List<Employee> returnedList = employeeRepository.getAllEmployees();
        List<Employee> expectedList = List.of(FIRST_EMP, SECOND_EMP, THIRD_EMP);

        assertEquals(returnedList, expectedList);
    }

    @Test
    void shouldReturnEmptyListOfEmployeesWhenDatabaseIsEmpty() {

        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.getAllEmployees())
                .thenReturn(employees);

        List<Employee> returnedList = employeeRepository.getAllEmployees();
        List<Employee> expectedList = Collections.emptyList();

        assertEquals(returnedList, expectedList);
    }

    @Test
    void shouldReturnListOfAllEmployeesWithProjectsCount() {

        final EmployeeProjectsCountDto FIRST_EMPL = new EmployeeProjectsCountDto("husband", "story", "firstemail.mail.com", 2);
        final EmployeeProjectsCountDto SECOND_EMPL = new EmployeeProjectsCountDto("case", "per", "secondmail.mail.com", 1);
        final EmployeeProjectsCountDto THIRD_EMPL = new EmployeeProjectsCountDto("John", "Doe", "testemail@mail.com", 3);

        List<EmployeeProjectsCountDto> employees = new ArrayList<>();
        employees.add(FIRST_EMPL);
        employees.add(SECOND_EMPL);
        employees.add(THIRD_EMPL);
        when(employeeRepository.countEmployeeProjects())
                .thenReturn(employees);

        List<EmployeeProjectsCountDto> returnedList = employeeRepository.countEmployeeProjects();
        List<EmployeeProjectsCountDto> expectedList = List.of(FIRST_EMPL, SECOND_EMPL, THIRD_EMPL);

        assertEquals(returnedList, expectedList);
    }

    @Test
    void shouldReturnUserFoundByEmailIfExistInDatabase() {

        final String email = "testemail@mail.com";

        when(employeeRepository.findByEmail(email))
                .thenReturn(Optional.of(THIRD_EMP));

        Employee returnedEmployee = employeeRepository.findByEmail(email).get();

        assertEquals(returnedEmployee, THIRD_EMP);
    }
    @Test
    @ExceptionHandler(BusinessException.class)
    void shouldReturnBusinessExceptionIfEmployeeNotExistInDatabase() {

        final String email = "bad_email@mail.com";

        when(employeeRepository.findByEmail(email))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.findByEmail(email));

        assertEquals("No such user", exception.getMessage());
    }


    @Test
    void shouldDeleteEmployeeIfEmployeeExists() {
        Long id = 123L;
        employeeService.deleteEmployee(id);
        verify(employeeRepository).remove(id);
    }

    @Test
    void shouldUpdateEmployeeIfExist() {
        employeeService.updateEmployee(THIRD_EMP);
        verify(employeeRepository).update(THIRD_EMP);
    }

}