package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void getTaskByID() {
        Task task = taskRepository.getTaskByID(1);
      assertThat(task).isNotNull();
      assertThat(task.getName()).isEqualTo("Web application");
      assertThat(task.getProjectID()).isEqualTo(1);
      assertThat(task.getParentID()).isEqualTo(0);
    }

    @Test
    void getAllTasksForProjects() {
        Set<Task> tasks = taskRepository.getAllTasksForProjects(1);
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks).isNotEmpty();
        }

    @Test
    void getAllSubTasks() {
    }

    @Test
    void getAllTasksForAccount() {
    }

    @Test
    void getAllAssignedToProject() {
    }
}