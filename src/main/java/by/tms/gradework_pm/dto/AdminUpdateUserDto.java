package by.tms.gradework_pm.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdminUpdateUserDto {
    private Long id;
    private String role;
}
