package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.entity.Stage;
import by.tms.gradework_pm.exception.BusinessException;

import java.time.Instant;
import java.util.List;

public interface ProjectService {

    Project findById(Long id) throws BusinessException;

    void saveNewProject(Project project);

    List<ProjectDto> getAllProjects();

    void removeProject(Long id);

    List<ProjectDto> findOpenProjectsByDate(Instant date);

    List<ProjectDto> findAllProjectsByStage(Stage stage);
}
