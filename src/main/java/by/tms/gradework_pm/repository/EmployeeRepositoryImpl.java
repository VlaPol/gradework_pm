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

    @Override
    public void update(Employee employee){
        Long id = employee.getId();
        Employee entity = entityManager.find(Employee.class, id);
        entity.setFirstName(employee.getFirstName());
        entity.setLastName(employee.getLastName());
        entity.setEmail(employee.getEmail());
        entityManager.persist(entity);
    }

    public List<EmployeeProjectsCountDto> countEmployeeProjects() {

        final String sql = """
                            SELECT e.first_name, e.last_name, e.email,
                            count(pe.project_id) as projectCount
                            FROM employee e LEFT JOIN project_emp pe on(e.id = pe.employee_id)
                            GROUP BY e.first_name, e.last_name, e.email ORDER BY projectCount DESC
                        """;

        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            EmployeeProjectsCountDto dto = new EmployeeProjectsCountDto();
            dto.setFirstName (rs.getString("FIRST_NAME"));
            dto.setLastName(rs.getString("LAST_NAME"));
            dto.setEmail(rs.getString("EMAIL"));
            dto.setProjectCount(rs.getInt("projectCount"));
            return dto;
        }));

    }

    @Override
    public List<Employee> getAllEmployees() {

        final String sql = """
                            SELECT e.id, e.first_name, e.last_name,
                            e.email
                            FROM employee e
                            ORDER BY e.last_name DESC
                        """;

        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            Employee dto = new Employee();
            dto.setId(rs.getLong("ID"));
            dto.setFirstName (rs.getString("FIRST_NAME"));
            dto.setLastName(rs.getString("LAST_NAME"));
            dto.setEmail(rs.getString("EMAIL"));
            return dto;
        }));
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return entityManager.createQuery("""
                        SELECT emp
                        FROM Employee emp
                        WHERE lower(emp.email) = lower(:email)
                        """, Employee.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
