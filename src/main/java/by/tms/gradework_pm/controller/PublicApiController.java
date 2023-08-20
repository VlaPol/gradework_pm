package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.dto.employee.EmployeeDto;
import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.service.EmployeeService;
import by.tms.gradework_pm.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicApiController {

    private final EmployeeService employeeService;
    private final ProjectService proService;

    @GetMapping("/employees")
    public String displayEmployees(Model model){
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees/list-employees";
    }

    @GetMapping("/projects")
    public String displayProjects(Model model) {
        List<ProjectDto> projects = proService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects/list-projects";
    }
}
