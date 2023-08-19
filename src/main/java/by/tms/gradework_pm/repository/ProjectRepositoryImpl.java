package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.entity.Stage;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectRepositoryImpl
        extends BaseRepositoryImpl<Project, Long>
        implements ProjectRepository {

    public ProjectRepositoryImpl(Class<Project> entityClass) {
        super(entityClass);
    }

    @Override
    public List<ProjectDto> findOpenProjectsByDate(Instant date){
        return entityManager.createQuery("""
                        SELECT p.name, p.description,
                            p.stage, p.startDate, p.endDate
                            FROM Project p ORDER BY p.startDate
                        """, ProjectDto.class)
                .getResultStream()
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> findAllByStage(Stage stage) {

        return entityManager.createQuery("""
                        SELECT p.name, p.description,
                            p.stage, p.startDate, p.endDate
                            FROM Project p
                            WHERE p.stage = upper(:stage)
                            ORDER BY stage
                        """, ProjectDto.class)
                .setParameter("stage", stage)
                .getResultStream()
                .collect(Collectors.toList());
    }
}
