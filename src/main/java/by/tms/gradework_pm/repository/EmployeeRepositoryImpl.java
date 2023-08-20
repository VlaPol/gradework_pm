package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.employee.EmployeeDto;
import by.tms.gradework_pm.dto.employee.EmployeeProjectsCountDto;
import by.tms.gradework_pm.entity.Employee;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepositoryImpl
        extends BaseRepositoryImpl<Employee, Long>
        implements EmployeeRepository {

    private final NamedParameterJdbcOperations jdbcTemplate;

    public EmployeeRepositoryImpl(NamedParameterJdbcOperations jdbcTemplate) {
        super(Employee.class);
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EmployeeProjectsCountDto> countEmployeeProjects() {

        final String sql = """
                            SELECT e.first_name, e.last_name,
                            count(pe.project_id) as projectCount
                            FROM employee e LEFT JOIN project_emp pe on(e.id = pe.employee_id)
                            GROUP BY e.first_name, e.last_name ORDER BY projectCount DESC
                        """;

        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            EmployeeProjectsCountDto dto = new EmployeeProjectsCountDto();
            dto.setFirstName (rs.getString("FIRST_NAME"));
            dto.setLastName(rs.getString("LAST_NAME"));
            dto.setProjectCount(rs.getInt("projectCount"));
            return dto;
        }));

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {

        final String sql = """
                            SELECT e.first_name, e.last_name,
                            e.email
                            FROM employee e
                            ORDER BY e.last_name DESC
                        """;

        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            EmployeeDto dto = new EmployeeDto();
            dto.setFirstName (rs.getString("FIRST_NAME"));
            dto.setLastName(rs.getString("LAST_NAME"));
            dto.setEmail(rs.getString("email"));
            return dto;
        }));
    }

    @Override
    public Optional<EmployeeDto> findByEmail(String email) {
        return entityManager.createQuery("""
                        SELECT emp
                        FROM Employee emp
                        WHERE lower(emp.email) = lower(:email)
                        """, EmployeeDto.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
