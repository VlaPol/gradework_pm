package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.ChartDate;
import by.tms.gradework_pm.dto.project.ActivProjectsDto;
import by.tms.gradework_pm.dto.project.ProjectDtoWithStringDate;
import by.tms.gradework_pm.dto.project.ReturnedActivProjectsDto;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.ProjectRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<ProjectDtoWithStringDate> getAllProjects() {

        List<Project> allProjectsInput = projectRepository.findAllProjects();
        List<ProjectDtoWithStringDate> allProjects = new ArrayList<>();
        for (Project itm : allProjectsInput) {
            ProjectDtoWithStringDate dto = new ProjectDtoWithStringDate();
            dto.setId(itm.getId().toString());
            dto.setName(itm.getName());
            dto.setStage(itm.getStage());
            dto.setStartDate(format.format(itm.getStartDate()));
            dto.setEndDate(format.format(itm.getEndDate()));
            allProjects.add(dto);
        }

        return allProjects;
    }


    @Override
    public Project findById(Long id) throws BusinessException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Проект с указанным id не обнаружен"));
    }

    @Override
    public void saveNewProject(Project project) {
        projectRepository.create(project);
    }

    @Override
    public void removeProject(Long id) {
        projectRepository.remove(id);
    }

    @Override
    public List<ReturnedActivProjectsDto> findOpenProjectsByDate() {

        Date date = Date.from(Instant.now());
        List<ActivProjectsDto> openProjectsByDate = projectRepository.findOpenProjectsByDate(date);

        HashMap<Long, ActivProjectsDto> tmpMap = new HashMap<>();

        for (ActivProjectsDto itm : openProjectsByDate) {
            Long tmpIndex = itm.getId();
            if (!tmpMap.containsKey(tmpIndex)) {
                tmpMap.put(tmpIndex, itm);
            } else {
                ActivProjectsDto tmpRecord;
                tmpRecord = tmpMap.get(tmpIndex);
                String replacedEmployee = tmpRecord.getEmployee();
                replacedEmployee = replacedEmployee + ", " + itm.getEmployee();
                tmpRecord.setEmployee(replacedEmployee);
            }
        }

        return getReturnedActivProjectsDtos(tmpMap);
    }

    private static List<ReturnedActivProjectsDto> getReturnedActivProjectsDtos(HashMap<Long, ActivProjectsDto> tmpMap) {
        List<ReturnedActivProjectsDto> returnedDto = new ArrayList<>();
        for (Map.Entry<Long, ActivProjectsDto> entry : tmpMap.entrySet()) {
            ActivProjectsDto inputDto = entry.getValue();
            ReturnedActivProjectsDto itm = new ReturnedActivProjectsDto();
            itm.setName(inputDto.getName());
            itm.setDateBegin(inputDto.getDateBegin());
            itm.setDateEnd(inputDto.getDateEnd());
            itm.setEmployee(inputDto.getEmployee());
            returnedDto.add(itm);
        }
        return returnedDto;
    }


    public List<ChartDate> getProjectStatus() {
        return projectRepository.getProjectStatus();
    }

    @Override
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

}
