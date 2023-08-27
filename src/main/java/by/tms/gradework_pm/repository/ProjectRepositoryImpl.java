package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.ChartDate;
import by.tms.gradework_pm.dto.project.ActivProjectsDto;
import by.tms.gradework_pm.dto.project.ProjectDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.entity.Stage;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
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
    public void update(Project project) {
        Long id = project.getId();
        Project entity = entityManager.find(Project.class, id);
        entity.setName(project.getName());
        entity.setDescription(project.getDescription());
        entity.setStage(project.getStage());
        entityManager.persist(entity);
    }

    @Override
    public List<ActivProjectsDto> findOpenProjectsByDate(Date date) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        final String sql = """
                SELECT p.id, p.name, p.start_date,
                p.end_date, e.last_name
                FROM project p
                LEFT JOIN project_emp pe ON pe.project_id = p.id
                LEFT JOIN employee e ON e.id = pe.employee_id
                WHERE p.end_date::date > (:date) AND p.start_date::date < (:date)
                GROUP BY p.id, p.name, p.start_date,
                p.end_date, e.last_name
                ORDER BY p.end_date DESC
                """;

        SqlParameterSource parametr = new MapSqlParameterSource("date", date);

        return jdbcTemplate.query(sql, parametr, (rs, rowNum) -> {
            ActivProjectsDto dto = new ActivProjectsDto();
            dto.setId(rs.getLong("ID"));
            dto.setName(rs.getString("NAME"));
            dto.setDateBegin(format.format(rs.getDate("START_DATE")));
            dto.setDateEnd(format.format(rs.getDate("END_DATE")));
            dto.setEmployee(rs.getString("LAST_NAME"));
            return dto;
        });
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

        return entityManager.createQuery("""
                        SELECT p
                        FROM Project p
                        ORDER BY p.name
                        """, Project.class)
                .getResultList();

    }

    public List<ChartDate> getProjectStatus() {

        final String sql = """
                SELECT stage as label,
                count(stage) as value FROM project
                GROUP BY stage
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ChartDate dto = new ChartDate();
            dto.setLabel(rs.getString("label"));
            dto.setValue(rs.getLong("value"));
            return dto;
        });
    }
}
