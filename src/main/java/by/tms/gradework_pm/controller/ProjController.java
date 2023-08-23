package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.dto.employee.EmployeeDto;
import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.service.EmployeeService;
import by.tms.gradework_pm.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjController {


    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @GetMapping("")
    public String displayProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects/list-projects";
    }

    @GetMapping("/new")
    public String displayProjectForm(Model model){

        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", employees);

        Project project = new Project();
        model.addAttribute("project", project);

        return "projects/new-project";
    }

    @PostMapping(value = "/save")
    public String createProject(@ModelAttribute("project") Project project,
                                 BindingResult bindingResult,
                                @ModelAttribute("allEmployees") Employee employee,
                                Model model){

        try {
            projectService.saveNewProject(project);
            return "redirect:/projects";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errorpages/error";
        }

    }

    @GetMapping("/update")
    public String displayProjUpdateForm(@RequestParam("id") Long id, Model model){

        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("allEmployees", employees);

        Project project = null;
        try {
            project = projectService.findById(id);
            model.addAttribute("project", project);
            return "projects/new-project";
        } catch (BusinessException e) {
            return "error";
        }

    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("id") Long id, Model model){

        try {
            Project proj = projectService.findById(id);
            projectService.removeProject(id);
            return "redirect:/projects";
        }catch (BusinessException e) {
            return "error";
        }
    }


}
