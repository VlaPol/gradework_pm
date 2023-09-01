package by.tms.gradework_pm.service;


import by.tms.gradework_pm.dto.RegistrationDto;
import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.exception.BusinessException;

public interface UserService {

    User findUserByEmail(String email);
    void saveUser(RegistrationDto registrationDto) throws BusinessException;

}
