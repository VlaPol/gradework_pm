package by.tms.gradework_pm.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "account")
public class UserAccount extends BaseEntity{


    @Column(name = "username")
    private String userName;

    private String email;

    private String password;

    @Column(name = "is_active")
    private boolean enabled = true;

}
