package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectRowMapper implements RowMapper<Project>
{

    @Override
    public Project mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Project project = new Project
                (
                        rs.getInt("ProjectID"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getBoolean("IsActive"),
                        rs.getDate("StartDate"),
                        rs.getDate("Deadline")
                );

        project.setHourEstimate(0);

        return project;
    }
}
