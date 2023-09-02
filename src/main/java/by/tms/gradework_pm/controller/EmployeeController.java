package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.service.EmployeeService;
import by.tms.gradework_pm.util.ProjectRoles;
import by.tms.gradework_pm.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("")
    public String displayEmployees(Model model) {

        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        if (isEquals()) {
            return "employees/list-employees";
        } else {
            return "employees/list-employees-user";
        }
    }

    @GetMapping("/new")
    public String showEmployee(Model model) {

        if (isEquals()) {
            Employee employee = new Employee();
            model.addAttribute("employee", employee);
            return "employees/new-employee";
        } else {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "employees/list-employees-user";
        }
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid Employee employee, Errors errors, Model model) {

        final String MESSAGE = "Employee is exist";

        if (isEquals()) {

            if (errors.hasErrors()) {
                return "employees/new-employee";
            }
            try {
                employeeService.createNewEmployee(employee);
                return "redirect:/employees";
            } catch (Exception e) {
                model.addAttribute("message", MESSAGE);
                return "errorpages/error";
            }
        } else {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "employees/list-employees-user";
        }

    }

    @GetMapping("/update")
    public String displayEmpUpdateForm(@RequestParam("email") String email,
                                       @ModelAttribute("employee") Employee employee,
                                       Model model) {

        final String MESSAGE = "Error on update employee";

        if (isEquals()) {
            if (employee.getId() == null) {
                try {
                    Employee emp = employeeService.findByEmail(email);
                    model.addAttribute("employee", emp);
                    return "employees/update-employee";
                } catch (BusinessException e) {
                    model.addAttribute("message", MESSAGE);
                    return "errorpages/error";
                }
            } else {
                employeeService.updateEmployee(employee);
                return "redirect:/employees";
            }
        } else {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "employees/list-employees-user";
        }

    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") Employee employee,
                                 Model model) {

        if (isEquals()) {
            employeeService.updateEmployee(employee);
            return "redirect:/employees";
        } else {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "employees/list-employees-user";
        }
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("empid") Long id, Model model) {
        if (isEquals()) {
            employeeService.deleteEmployee(id);
            return "redirect:/employees";
        } else {
            List<Employee> employees = employeeService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "employees/list-employees-user";
        }
    }

    private static boolean isEquals() {
        return SecurityUtil.getRole()
                .equals(ProjectRoles.ROLE_ADMIN.name());
    }

}



