package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
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
        List<Task> tasks = taskRepository.getAllTasksForProjects(1);
        assertThat(tasks.size()).isEqualTo(2);
        assertThat(tasks).isNotEmpty();
        }

    @Test
    void getAllSubTasks() {
        assertThat(taskRepository.getAllSubTasks(1).size()).isEqualTo(1);
        assertThat(taskRepository.getAllSubTasks(2).size()).isEqualTo(2);
    }

    @Test
    void getAllTasksForAccount() {
        assertThat(taskRepository.getAllTasksForAccount(1).size()).isEqualTo(2);
        assertThat(taskRepository.getAllTasksForAccount(2).size()).isEqualTo(1);
    }

    @Test
    void getAllAssignedToProject() {
        assertThat(taskRepository.getAllAccountsAssignedToTask(1).size()).isEqualTo(2);
        assertThat(taskRepository.getAllAccountsAssignedToTask(2).size()).isEqualTo(1);
    }

    @Test
    void assignAccountToTask() {
        int assignedToTask = taskRepository.getAllAccountsAssignedToTask(2).size();
        taskRepository.assignAccountToTask(2,2);
        assertThat(taskRepository.getAllAccountsAssignedToTask(2).size()).isEqualTo(assignedToTask+1);
    }

    @Test
    void addTask() {
        int numberOfTasksForProject = taskRepository.getAllTasksForProjects(1).size();
        Task task = new Task(0,"Web application part 3", "_Adaptive Probelmsolvers_ need a new webplatform. It nee...",50, true, new Date(25-12-06), new Date(2026-01-28), 1, 0);
        boolean inserted = taskRepository.addTask(task);
        Task taskInDatabase = taskRepository.getTaskByID(6);
        assertThat(inserted).isEqualTo(true);
        assertThat(taskInDatabase.getName()).isEqualTo("Web application part 3");
        assertThat(taskRepository.getAllTasksForProjects(1).size()).isEqualTo(numberOfTasksForProject+1);
        assertThat(taskInDatabase.getProjectID()).isEqualTo(1);
    }

    @Test
    void addSubTask() {
        int numberOfTasksForProject = taskRepository.getAllTasksForProjects(1).size();
        Task task = new Task(0,"Web application part 3", "_Adaptive Probelmsolvers_ need a new webplatform. It nee...",50, true, new Date(25-12-06), new Date(2026-01-28), 1, 1);
        boolean inserted = taskRepository.addTask(task);
        Task taskInDatabase = taskRepository.getTaskByID(6);
        assertThat(inserted).isEqualTo(true);
        assertThat(taskInDatabase.getName()).isEqualTo("Web application part 3");
        assertThat(taskRepository.getAllTasksForProjects(1).size()).isEqualTo(numberOfTasksForProject);
        assertThat(taskInDatabase.getParentID()).isEqualTo(1);
    }

    @Test
    void updateTask() {
        int tasks = taskRepository.getAllTasksForProjects(1).size();
        Task task = taskRepository.getTaskByID(1);
        task.setName("Not a Web application");
        taskRepository.updateTask(task);
        Task updatedTask = taskRepository.getTaskByID(1);
        assertThat(updatedTask.getName()).isEqualTo("Not a Web application");
        assertThat(updatedTask.getProjectID()).isEqualTo(1);
        assertThat(tasks).isEqualTo(2);

    }

    @Test
    void deleteTask() {
        int tasks = taskRepository.getAllTasksForProjects(1).size();
        boolean removedTask = taskRepository.deleteTask(1);
        int tasksWithOneRemoved = taskRepository.getAllTasksForProjects(1).size();
        assertThat(tasksWithOneRemoved).isEqualTo(tasks-1);
        assertThat(removedTask).isEqualTo(true);
    }


}