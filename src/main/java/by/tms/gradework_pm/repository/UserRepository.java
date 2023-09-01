package by.tms.gradework_pm.repository;


import by.tms.gradework_pm.entity.principle.Role;
import by.tms.gradework_pm.entity.principle.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserByEmail(String email);
    void saveUser(User user);
    Optional<Role> findByRole(String role);
}
