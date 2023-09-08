package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.AdminUpdateUserDto;
import by.tms.gradework_pm.dto.AdminUserDto;
import by.tms.gradework_pm.repository.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public List<AdminUserDto> getAllUsers() {

        List<AdminUserDto> allUsers = adminRepository.getAllUsers();
        for (AdminUserDto itm : allUsers) {
            itm.setRole(itm.getRole().substring(itm.getRole().indexOf('_') + 1));
        }
        return allUsers;
    }


    @Override
    public void updateUserStatusAndRole(AdminUpdateUserDto user) {
        AdminUserDto newUser = new AdminUserDto();
        newUser.setRole(user.getRole());
        newUser.setId(user.getId());
        newUser.setEnabled(true);
        adminRepository.updateUsertRole(newUser);
    }
}
