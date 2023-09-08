package by.tms.gradework_pm.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdminUserDto {

    private Long id;
    private String userName;
    private String email;
    private boolean enabled;
    private String role;
}
