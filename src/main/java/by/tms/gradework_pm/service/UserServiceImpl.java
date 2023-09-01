package by.tms.gradework_pm.service;

import by.tms.gradework_pm.util.ProjectRoles;
import by.tms.gradework_pm.dto.RegistrationDto;
import by.tms.gradework_pm.entity.principle.Role;
import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void saveUser(RegistrationDto registrationDto) throws BusinessException {

        User user = new User();
        user.setUserName(registrationDto.getName());
        user.setEmail(registrationDto.getEmail());

        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        Role role = userRepository.findByRole(ProjectRoles.ROLE_GUEST.name())
                .orElseThrow(() -> new BusinessException("No such role"));
        user.setRoles(new HashSet<>(Collections.singletonList(role)));
        userRepository.saveUser(user);
    }

    @Override
    public User findUserByEmail(String email) {

        Optional<User> userByEmail = userRepository.getUserByEmail(email);
        return userByEmail.orElse(null);

    }

}
