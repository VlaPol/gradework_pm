package by.tms.gradework_pm.repository;

import by.tms.gradework_pm.dto.AdminUserDto;
import by.tms.gradework_pm.entity.principle.User;

import java.util.List;

public interface AdminRepository {

    List<AdminUserDto> getAllUsers();

    void updateUsertRole(AdminUserDto user);

}