package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.AdminUserDto;
import by.tms.gradework_pm.dto.project.ActivProjectsDto;
import by.tms.gradework_pm.entity.principle.Role;
import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.util.ProjectRoles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private final NamedParameterJdbcOperations jdbcTemplate;
    @PersistenceContext
    protected EntityManager entityManager;

    public AdminRepositoryImpl(NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AdminUserDto> getAllUsers() {
        final String sql = """
                    SELECT u.id, u.username, u.email, r.role, u.is_active
                    FROM users u
                             LEFT JOIN users_roles ur ON u.id = ur.user_id
                             LEFT JOIN roles r ON ur.role_id = r.id
                    ORDER BY u.username
                """;

        return new ArrayList<>(jdbcTemplate.query(sql, (rs, rowNum) -> {
            AdminUserDto dto = new AdminUserDto();
            dto.setId(rs.getLong("ID"));
            dto.setUserName(rs.getString("USERNAME"));
            dto.setEmail(rs.getString("EMAIL"));
            dto.setEnabled(rs.getBoolean("IS_ACTIVE"));
            dto.setRole(rs.getString("ROLE"));
            return dto;
        }));
    }

    @Override
    public void updateUsertRole(AdminUserDto user) {

        Integer roleId = getRoleIdByName(user.getRole());

        String sql = """
                    UPDATE users_roles
                    SET role_id = :roleId
                    WHERE user_id = :userId
                """;
        final Map<String, Object> params = new HashMap<>();
                params.put("roleId", roleId);
                params.put("userId", user.getId());
        jdbcTemplate.update(sql, params);

    }

    private Integer getRoleIdByName(String role) {

        String prepRole = "ROLE_" + role;

        String sql = """
                SELECT id FROM roles
                WHERE role = :role
                """;
        final Map<String, Object> params = Collections.singletonMap("role", prepRole);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);


    }
}
