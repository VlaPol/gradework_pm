package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.entity.Stage;

import java.time.Instant;
import java.util.List;

public interface ProjectRepository extends BaseRepository<Project, Long> {

    List<ProjectDto> findOpenProjectsByDate(Instant date);

    List<ProjectDto> findAllByStage(Stage stage);

    List<Project> findAllProjects();

}