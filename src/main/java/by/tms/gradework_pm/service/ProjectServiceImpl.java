package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.entity.Stage;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAllProjects();
    }


    @Override
    public Project findById(Long id) throws BusinessException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Проект с указанным id не обнаружен"));
    }

    @Override
    public void saveNewProject(Project project) {
        projectRepository.create(project);
    }

    @Override
    public void removeProject(Long id) {
        projectRepository.remove(id);
    }

    @Override
    public List<ProjectDto> findOpenProjectsByDate(Instant date) {
        return projectRepository.findOpenProjectsByDate(date);
    }

    @Override
    public List<ProjectDto> findAllProjectsByStage(Stage stage) {
        return projectRepository.findAllByStage(stage);
    }
}
