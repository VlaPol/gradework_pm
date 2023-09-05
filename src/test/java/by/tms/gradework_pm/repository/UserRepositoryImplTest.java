package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.service.EmployeeServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserRepositoryImplTest implements BaseRepository {

    @Mock
    EntityManager entityManager;
    UserRepository userRepository;

    @BeforeEach
    void setRepository() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    void getUserByEmailIfExists() {

        String email = "john.doe@example.com";
        User user = new User();
        user.setEmail(email);

        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultStream()).thenReturn(Stream.of(user));

        // Act
        Optional<User> result = userRepository.getUserByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());

        verify(entityManager).createQuery(anyString(), eq(User.class));
        verify(query).setParameter("email", email);
        verify(query).getResultStream();
    }

    @Test
    void saveUser() {
    }

    @Test
    void findByRole() {
    }

    @Override
    public Optional findById(Object o) {
        return Optional.empty();
    }

    @Override
    public void create(Object entity) {

    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public void update(Object entity) {

    }
}