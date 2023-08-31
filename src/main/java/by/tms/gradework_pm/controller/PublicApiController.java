package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.dto.ChartDate;
import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.dto.project.ProjectDtoWithStringDate;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.service.EmployeeService;
import by.tms.gradework_pm.service.ProjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ProjectService projectService;

    @GetMapping
    public String getMainPage(Model model) {

        List<ProjectDtoWithStringDate> projectList = projectService.getAllProjects();
        List<EmployeeProjectsCountDto> employeesList = employeeService.getEmployeeWithProjectsCount();

        model.addAttribute("projectsList", projectList);
        model.addAttribute("employeesList", employeesList);

        //------- Data fo diagram  --------
        List<ChartDate> projectData = projectService.getProjectStatus();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(projectData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("projectStatusCnt", jsonString);
        //*******************************************************************

        return "main/home";
    }


}
