package by.tms.gradework_pm.controller;

import by.tms.gradework_pm.aop.LogActionsAspect;
import by.tms.gradework_pm.dto.AdminUpdateUserDto;
import by.tms.gradework_pm.dto.AdminUserDto;
import by.tms.gradework_pm.service.AdminServiceImpl;
import by.tms.gradework_pm.util.ProjectRoles;
import by.tms.gradework_pm.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("")
    public String displayEmployees(Model model) {
        List<AdminUserDto> users = adminService.getAllUsers();
        List<String> roles = List.of(ProjectRoles.ROLE_ADMIN.getDisplayRole(),
                ProjectRoles.ROLE_USER.getDisplayRole(),
                ProjectRoles.ROLE_GUEST.getDisplayRole());
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "admin/admin";
    }

    @PostMapping("/update")
    @LogActionsAspect
    public String updateEmployee(@ModelAttribute("user") AdminUpdateUserDto user) {
        if (isEquals()) {
            adminService.updateUserStatusAndRole(user);
        }
        return "redirect:/admin";
    }

    private static boolean isEquals() {
        return SecurityUtil.getRole()
                .equals(ProjectRoles.ROLE_ADMIN.name());
    }
}
