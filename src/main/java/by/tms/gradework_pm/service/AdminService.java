package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.AdminUpdateUserDto;
import by.tms.gradework_pm.dto.AdminUserDto;
import by.tms.gradework_pm.entity.principle.User;
import by.tms.gradework_pm.exception.BusinessException;

import java.util.List;

public interface AdminService {

    List<AdminUserDto> getAllUsers();
    void updateUserStatusAndRole(AdminUpdateUserDto user);

    //User findById(Long id) throws BusinessException;
}
