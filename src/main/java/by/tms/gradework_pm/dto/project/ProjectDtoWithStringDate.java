package by.tms.gradework_pm.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDtoWithStringDate {

    private String Id;
    private String name;
    private String stage;
    private String description;
    private String startDate;
    private String endDate;
}
