package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.ChartDate;
import by.tms.gradework_pm.dto.project.ProjectDtoWithStringDate;
import by.tms.gradework_pm.dto.project.ReturnedActivProjectsDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.exception.BusinessException;

import java.util.List;

public interface ProjectService {

    Project findById(Long id) throws BusinessException;

    void saveNewProject(Project project);

    List<ProjectDtoWithStringDate> getAllProjects();

    void removeProject(Long id);

    List<ReturnedActivProjectsDto> findOpenProjectsByDate();

    List<ChartDate> getProjectStatus();

    void updateProject(Project project);

}
