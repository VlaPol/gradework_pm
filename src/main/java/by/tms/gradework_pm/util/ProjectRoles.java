package by.tms.gradework_pm.util;

import lombok.Getter;

@Getter
public enum ProjectRoles {
    ROLE_ADMIN ("ADMIN"),
    ROLE_USER ("USER"),
    ROLE_GUEST ("GUEST");

    private final String displayRole;

    ProjectRoles(String displayRole) {
        this.displayRole = displayRole;
    }
}
