package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.dto.project.ProjectDtoWithStringDate;
import by.tms.gradework_pm.dto.project.ReturnedActivProjectsDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.service.EmployeeService;
import by.tms.gradework_pm.service.ProjectService;
import by.tms.gradework_pm.util.ProjectRoles;
import by.tms.gradework_pm.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        List<ProjectDtoWithStringDate> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);

        if (isEquals()) {
            return "projects/list-projects";
        } else {
            return "projects/list-projects-user";
        }
    }

    @GetMapping("/new")
    public String displayProjectForm(Model model) {

        if (isEquals()) {
            Project project = new Project();
            List<Employee> employees = employeeService.getAllEmployees();
            project.setEmployees(employees);
            model.addAttribute("project", project);
            model.addAttribute("employees", employees);

            return "projects/new-project";
        } else {
            List<ProjectDtoWithStringDate> projects = projectService.getAllProjects();
            model.addAttribute("projects", projects);
            return "projects/list-projects-user";
        }
    }

    @PostMapping("/save")
    public String createProject(Project project, BindingResult bindingResult, Model model) {

        if (isEquals()) {
            try {
                projectService.saveNewProject(project);
                return "redirect:/projects";
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
                return "errorpages/error";
            }
        } else {
            List<ProjectDtoWithStringDate> projects = projectService.getAllProjects();
            model.addAttribute("projects", projects);
            return "projects/list-projects-user";
        }

    }

    @GetMapping("/update")
    public String displayProjUpdateForm(@RequestParam("id") Long id,
                                        @ModelAttribute("project") Project project,
                                        Model model) {

        final String MESSAGE = "Error on project update";

        if (isEquals()) {
            try {
                Project tmpProject = projectService.findById(id);
                model.addAttribute("project", tmpProject);
                return "projects/update-project";
            } catch (BusinessException e) {
                model.addAttribute("message", MESSAGE);
                return "errorpages/error";
            }
        } else {
            List<ProjectDtoWithStringDate> projects = projectService.getAllProjects();
            model.addAttribute("projects", projects);
            return "projects/list-projects-user";
        }
    }

    @PostMapping("/update")
    public String updateProject(@ModelAttribute("project") Project project,
                                Model model) {

        final String MESSAGE = "Error on project update";

        if (isEquals()) {
            projectService.updateProject(project);
            return "redirect:/projects";
        } else {
            List<ProjectDtoWithStringDate> projects = projectService.getAllProjects();
            model.addAttribute("projects", projects);
            return "projects/list-projects-user";
        }

    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("id") Long id, Model model) {

        if (isEquals()) {
            try {
                Project proj = projectService.findById(id);
                projectService.removeProject(id);
                return "redirect:/projects";
            } catch (BusinessException e) {
                return "errorpages/error";
            }
        } else {
            List<ProjectDtoWithStringDate> projects = projectService.getAllProjects();
            model.addAttribute("projects", projects);
            return "projects/list-projects-user";
        }
    }

    @GetMapping("/active")
    public String getActiveProjects(Model model) {

        List<ReturnedActivProjectsDto> openProjectsByDate = projectService.findOpenProjectsByDate();

        model.addAttribute("openProjects", openProjectsByDate);

        return "projects/activ-projects";

    }

    private static boolean isEquals() {
        return SecurityUtil.getRole()
                .equals(ProjectRoles.ROLE_ADMIN.name());
    }

}
