package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.service.EmployeeService;
import by.tms.gradework_pm.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PublicApiController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @GetMapping
    public String getMainPage(Model model){

        List<Project> projectList = projectService.getAllProjects();
        List<EmployeeProjectsCountDto> employeesList = employeeService.getEmployeeWithProjectsCount();

        model.addAttribute("projectsList", projectList);
        model.addAttribute("employeesList", employeesList);

        return "main/home";
    }


}
