package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.entity.Stage;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProjectRepositoryImpl
        extends BaseRepositoryImpl<Project, Long>
        implements ProjectRepository {

    private final NamedParameterJdbcOperations jdbcTemplate;

    public ProjectRepositoryImpl(NamedParameterJdbcOperations jdbcTemplate) {
        super(Project.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(Project project){
        Long id = project.getId();
        Project entity = entityManager.find(Project.class, id);
        project.setName(project.getName());
        project.setDescription(project.getDescription());
        project.setStage(project.getStage());
        project.setStartDate(project.getStartDate());
        project.setEndDate(project.getEndDate());
        entityManager.persist(entity);
    }

    @Override
    public List<ProjectDto> findOpenProjectsByDate(Instant date) {
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

    @Override
    public List<Project> findAllProjects() {

        final String sql = """
                    SELECT p.id, p.name, p.stage,
                     p.start_date, p.end_date
                    FROM project p
                    ORDER BY p.name
                """;

        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            Project dto = new Project();
            dto.setId(rs.getLong("ID"));
            dto.setName(rs.getString("NAME"));
            dto.setStage(rs.getString("STAGE"));
            dto.setStartDate(rs.getDate("START_DATE"));
            dto.setEndDate(rs.getDate("END_DATE"));
            return dto;
        }));
    }
}
