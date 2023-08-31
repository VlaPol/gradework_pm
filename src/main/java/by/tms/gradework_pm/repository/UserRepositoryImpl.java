package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.entity.principle.Role;
import by.tms.gradework_pm.entity.principle.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return entityManager.createQuery("""
                        SELECT u FROM User u WHERE u.email = :email
                        """, User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public Optional<Role> findByRole(String role) {
        return entityManager.createQuery("""
                        SELECT r FROM Role r WHERE r.role = :role
                        """, Role.class)
                .setParameter("role", role)
                .getResultStream()
                .findFirst();
    }

}
