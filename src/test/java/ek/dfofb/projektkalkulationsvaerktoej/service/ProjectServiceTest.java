package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.repository.ProjectRepository;
import ek.dfofb.projektkalkulationsvaerktoej.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest
{

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void getAllProjects_returnsListFromRepository()
    {
        Project p1 = new Project(1, "Projekt1", "Beskrivelse1", true, new Date(), new Date());
        Project p2 = new Project(1, "Projekt2", "Beskrivelse2", true, new Date(), new Date());
        List<Project> projects = Arrays.asList(p1, p2);

        when(projectRepository.getAllProjects()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertEquals(2, result.size());
        assertEquals("Projekt1", result.get(0).getName());
        verify(projectRepository, times(1)).getAllProjects();
        verifyNoMoreInteractions(projectRepository, taskRepository);
    }

    @Test
    void getProjectByID_calculatesHourEstimateFromTasks()
    {
        int projectId = 1;
        Project project = new Project(projectId, "Test-pro", "Beskrivelse", true, new Date(), new Date());

        Task t1 = new Task();
        t1.setHourEstimate(5);
        Task t2 = new Task();
        t2.setHourEstimate(2);

        when(projectRepository.getProjectByID(projectId)).thenReturn(project);
        when(taskRepository.getAllTasksForProjects(projectId)).thenReturn(Arrays.asList(t1, t2));

        Project result = projectService.getProjectByID(projectId);

        assertEquals(7, result.getHourEstimate());
        verify(projectRepository, times(1)).getProjectByID(projectId);
        verify(taskRepository, times(1)).getAllTasksForProjects(projectId);
        verifyNoMoreInteractions(projectRepository, taskRepository);
    }

    @Test
    void getProjectByID_handlesNoTaskWithZeroEstimate()
    {
        int projectId = 99;
        Project project = new Project(projectId, "TomtProjekt", "IngenOpgaver", true, new Date(), new Date());

        when(projectRepository.getProjectByID(projectId)).thenReturn(project);
        when(taskRepository.getAllTasksForProjects(projectId)).thenReturn(Collections.emptyList());

        Project result = projectService.getProjectByID(projectId);

        assertEquals(0, result.getHourEstimate());
        verify(projectRepository).getProjectByID(projectId);
        verify(taskRepository).getAllTasksForProjects(projectId);
    }

    @Test
    void createProject_returnsTrueWhenRepositorySucceeds() {

        Project project = new Project(0, "NytProjekt", "EnBeskrivelse", true, new Date(), new Date());
        when(projectRepository.addProject(project)).thenReturn(true);

        boolean result = projectService.createProject(project);

        assertTrue(result);
        verify(projectRepository, times(1)).addProject(project);
        verifyNoMoreInteractions(projectRepository, taskRepository);
    }

    @Test
    void updateProject_delegatesToRepository() {
        Project project = new Project(1, "OpdateretProjekt", "Beskrivelse", true, new Date(), new Date());
        when(projectRepository.updateProject(project)).thenReturn(project);

        Project result = projectService.updateProject(project);

        assertSame(project, result);
        verify(projectRepository, times(1)).updateProject(project);
        verifyNoMoreInteractions(projectRepository, taskRepository);
    }
}
