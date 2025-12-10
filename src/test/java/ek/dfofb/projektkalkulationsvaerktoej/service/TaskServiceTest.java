package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    void timeUntilDeadline() {
        assertThat(taskService.timeUntilDeadline(1)).
                isEqualTo((int) ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.of(2026,01,28)));
    }

    @Test
    void hoursLeftOnTask() {

    assertThat(taskService.hoursLeftOnTask(2,false)).isEqualTo(200);
    assertThat(taskService.hoursLeftOnTask(1,false)).isEqualTo(0);

    }

    @Test
    void percentOfProgressDone() {
        assertThat(taskService.percentOfProgressDone(1)).isEqualTo(100);
        assertThat(taskService.percentOfProgressDone(2)).isEqualTo(33);
    }

    @Test
    void markAsDone() {
        taskService.markAsDone(1,100);
        assertThat(taskService.getTaskByID(1).getIsCompleted()).isEqualTo(false);
        taskService.markAsDone(1,110);
        assertThat(taskService.getTaskByID(1).getIsCompleted()).isEqualTo(true);

    }
}