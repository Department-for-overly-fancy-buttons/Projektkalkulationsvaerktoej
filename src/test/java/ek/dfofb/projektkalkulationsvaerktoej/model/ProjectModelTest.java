package ek.dfofb.projektkalkulationsvaerktoej.model;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectModelTest
{

    @Test
    void constructor_fieldsPlacedCorrectly()
    {
        Date start = new Date(02-02-2002);
        Date deadline = new Date(02-02-2002);

        Project project = new Project(1, "projektNavn", "beskrivelse", true, start, deadline);

        assertEquals(1, project.getProjectID());
        assertEquals("projektNavn", project.getName());
        assertEquals("beskrivelse", project.getDescription());
        assertTrue(project.getIsActive());
        assertEquals(start, project.getStartDate());
        assertEquals(deadline, project.getDeadline());
    }

    @Test
    void settersAndGetters_worksAsWeIntended()
    {
        Project project = new Project();
        Date start = new Date(02-02-2002);
        Date deadline = new Date(02-02-2002);

        project.setName("Test");
        project.setDescription("Beskr");
        project.setIsActive(false);
        project.setStartDate(start);
        project.setDeadline(deadline);
        project.setHourEstimate(42);

        assertEquals("Test", project.getName());
        assertEquals("Beskr", project.getDescription());
        assertFalse(project.getIsActive());
        assertEquals(start, project.getStartDate());
        assertEquals(deadline, project.getDeadline());
        assertEquals(42, project.getHourEstimate());
    }


}
