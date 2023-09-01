package by.tms.gradework_pm.dto.project;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReturnedActivProjectsDto {

    private String name;
    private String description;
    private String dateBegin;
    private String dateEnd;
    private String employee;

}
