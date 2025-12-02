package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void getAllProjects_returnsProjectsFromDatabase()
    {
        List<Project> projects = projectRepository.getAllProjects();

        assertFalse(projects.isEmpty());
    }

    @Test
    void addProject_insertsRowInDatabase()
    {

        Project p = new Project(
                0,
                "Integration test project",
                "Created from test",
                true,
                java.sql.Date.valueOf("2025-01-01"),
                java.sql.Date.valueOf("2025-12-31")
        );

        boolean result = projectRepository.addProject(p);
        List<Project> projects = projectRepository.getAllProjects();

        assertTrue(result);
        assertTrue(projects.stream()
                .anyMatch(pr -> "Integration test project".equals(pr.getName())));
    }
}