package ek.dfofb.projektkalkulationsvaerktoej.model;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectModelTest
{

    @Test
    void constructor_fieldsPlacedCorrectly()
    {
        Date start = new Date();
        Date deadline = new Date();

        Project project = new Project(1, "projektNavn", "beskrivelse", true, start, deadline);

        assertEquals(1, project.getProjectID());
        assertEquals("projektNavn", project.getName());
        assertEquals("beskrivelse", project.getDescription());
        assertTrue(project.isActive());
        assertEquals(start, project.getStartDate());
        assertEquals(deadline, project.getDeadline());
    }

    @Test
    void settersAndGetters_worksAsWeIntended()
    {
        Project project = new Project();
        Date start = new Date();
        Date deadline = new Date();

        project.setName("Test");
        project.setDescription("Beskr");
        project.setActive(false);
        project.setStartDate(start);
        project.setDeadline(deadline);
        project.setHourEstimate(42);

        assertEquals("Test", project.getName());
        assertEquals("Beskr", project.getDescription());
        assertFalse(project.isActive());
        assertEquals(start, project.getStartDate());
        assertEquals(deadline, project.getDeadline());
        assertEquals(42, project.getHourEstimate());
    }


}
