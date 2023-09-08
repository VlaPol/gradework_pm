package by.tms.gradework_pm.service;

import by.tms.gradework_pm.dto.project.ActivProjectsDto;
import by.tms.gradework_pm.dto.project.ReturnedActivProjectsDto;
import by.tms.gradework_pm.entity.Employee;
import by.tms.gradework_pm.entity.Project;
import by.tms.gradework_pm.exception.BusinessException;
import by.tms.gradework_pm.repository.EmployeeRepository;
import by.tms.gradework_pm.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceImplTest {

    ProjectRepository projectRepository;
    ProjectService projectService;

    public static final List<Employee> employees = null;
    public static final Project FIRST_PROJ = new Project("shirt", "small", "reply", Date.from(Instant.now()), Date.from(Instant.now()), employees);
    public static final Project SECOND_PROJ = new Project("room", "sour", "request", Date.from(Instant.now()), Date.from(Instant.now()), employees);
    public static final Project THIRD_PROJ = new Project("circle", "hollow", "pretty", Date.from(Instant.now()), Date.from(Instant.now()), employees);

    @BeforeEach
    void setRepository() {
        projectRepository = mock(ProjectRepository.class);
        projectService = new ProjectServiceImpl(projectRepository);
    }

    @Test
    void shouldReturnListOfAllProjects() {

        List<Project> projects = new ArrayList<>();
        projects.add(FIRST_PROJ);
        projects.add(SECOND_PROJ);
        projects.add(THIRD_PROJ);
        when(projectRepository.findAllProjects())
                .thenReturn(projects);

        List<Project> returnedList = projectRepository.findAllProjects();
        List<Project> expectedList = List.of(FIRST_PROJ, SECOND_PROJ, THIRD_PROJ);

        assertEquals(returnedList, expectedList);
    }

    @Test
    void shouldReturnEmptyListOfProjectsWhenDatabaseIsEmpty() {

        List<Project> projects = new ArrayList<>();
        when(projectRepository.findAllProjects())
                .thenReturn(projects);

        List<Project> returnedList = projectRepository.findAllProjects();
        List<Project> expectedList = Collections.emptyList();

        assertEquals(returnedList, expectedList);
    }

    @Test
    void shouldReturnProjectIfFindById() {
        final Long id = 123L;

        when(projectRepository.findById(id))
                .thenReturn(Optional.of(FIRST_PROJ));
        Project returnedProject = projectRepository.findById(id).get();
        assertEquals(returnedProject, FIRST_PROJ);
    }

    @Test
    @ExceptionHandler(BusinessException.class)
    void shouldReturnBusinessExceptionIfProjectNotExistInDatabase() {

        final Long id = 123L;

        when(projectRepository.findById(id))
                .thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> projectService.findById(id));
        assertEquals("Проект с указанным id не обнаружен", exception.getMessage());
    }

    @Test
    void shouldCreateNewProject() {
        projectService.saveNewProject(FIRST_PROJ);
        verify(projectRepository).create(FIRST_PROJ);
    }

    @Test
    void shouldDeleteProjectIfExist() {

        Long id = 123L;
        projectService.removeProject(id);
        verify(projectRepository).remove(id);
    }

    @Test
    void shouldUpdateProjectIfExist() {
        projectService.updateProject(THIRD_PROJ);
        verify(projectRepository).update(THIRD_PROJ);
    }

    @Test
    void shouldReturnOpenProjectsOnDate() {

        Date date = Date.from(Instant.now());
        List<ActivProjectsDto> openProjectsByDate = new ArrayList<>();

        when(projectRepository.findOpenProjectsByDate(date))
                .thenReturn(openProjectsByDate);

        List<ReturnedActivProjectsDto> returnedProjects = projectService.findOpenProjectsByDate();
        assertEquals(openProjectsByDate.size(), returnedProjects.size());
    }

    @Test
    void shouldReturnEmptyListOfProjectsOnDateIfDoesntExists() {

        Date date = Date.from(Instant.now());
        List<ActivProjectsDto> emptyProjectsByDate = new ArrayList<>();

        when(projectRepository.findOpenProjectsByDate(date))
                .thenReturn(emptyProjectsByDate);

        List<ReturnedActivProjectsDto> returnedProjects = projectService.findOpenProjectsByDate();
        assertTrue(returnedProjects.isEmpty());
    }

}