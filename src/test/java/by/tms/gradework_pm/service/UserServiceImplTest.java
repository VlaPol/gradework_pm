package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.RegistrationDto;
import by.tms.gradework_pm.entity.principle.Role;
import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.UserRepository;
import by.tms.gradework_pm.util.ProjectRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {


    private BCryptPasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private UserService userService;


    @BeforeEach
    void setRepository() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void shouldSaveUserSuccessfully() throws BusinessException {

        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setName("John Doe");
        registrationDto.setEmail("john.doe@example.com");
        registrationDto.setPassword("password123");

        Role role = new Role();
        role.setRole(ProjectRoles.ROLE_GUEST.name());

        when(userRepository.findByRole(ProjectRoles.ROLE_GUEST.name()))
                .thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password123"))
                .thenReturn("encodedPassword");

        userService.saveUser(registrationDto);

        verify(passwordEncoder).encode("password123");
    }

    @Test
    @ExceptionHandler(BusinessException.class)
    void shouldThrowBusinessExceptionIfRoleNotFound() {

        RegistrationDto registrationDto = new RegistrationDto();
        when(userRepository.findByRole(ProjectRoles.ROLE_GUEST.name()))
                .thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> userService.saveUser(registrationDto));
    }

    @Test
    void shouldReturnUserByEmail() {

        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        when(userRepository.getUserByEmail(email)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findUserByEmail(email);
        verify(userRepository).getUserByEmail(email);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void shouldReturnNullForNonexistentEmail() {

        String email = "nonexistent@example.com";
        when(userRepository.getUserByEmail(email))
                .thenReturn(Optional.empty());

        User actualUser = userService.findUserByEmail(email);
        verify(userRepository).getUserByEmail(email);
        assertEquals(null, actualUser);
    }
}