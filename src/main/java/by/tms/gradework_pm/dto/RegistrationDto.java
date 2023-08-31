package by.tms.gradework_pm.dto;

import by.tms.gradework_pm.entity.BaseEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto extends BaseEntity {

    @NotEmpty
    private String name;

    @NotEmpty(message = "Email should not be empty or null")
    @Email
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;
}
