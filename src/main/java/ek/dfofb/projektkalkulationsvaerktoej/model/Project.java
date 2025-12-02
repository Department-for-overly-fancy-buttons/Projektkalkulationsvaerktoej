package ek.dfofb.projektkalkulationsvaerktoej.model;

import java.sql.Date;
import java.util.Set;

public class Project
{
    private int projectID;
    private String name;
    private String description;
    private boolean isActive;
    private int hourEstimate; // Jeg tænker vi ønsker at beregne denne ud fra tasks, så venter lige til den er færdig
    private Date startDate;
    private Date deadline;
    private Set<Account> assignedWorkers;
    private Set<Task> tasks;

    public Project()
    {

    }

    public Project(int projectID, String name, String description, boolean isActive, Date startDate, Date deadline)
    {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.startDate = startDate;
        this.deadline = deadline;
    }

    public int getProjectID()
    {
        return projectID;
    }

    public int getHourEstimate()
    {
        return hourEstimate;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean getIsActive()
    {
        return isActive;
    }

    public void setActive(boolean active)
    {
        isActive = active;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getDeadline()
    {
        return deadline;
    }

    public void setDeadline(Date deadline)
    {
        this.deadline = deadline;
    }

    public Set<Account> getAssignedWorkers()
    {
        return assignedWorkers;
    }

    public void setAssignedWorkers(Set<Account> assignedWorkers)
    {
        this.assignedWorkers = assignedWorkers;
    }

    public Set<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(Set<Task> tasks)
    {
        this.tasks = tasks;
    }
    public void setHourEstimate(int hourEstimate)
    {
        this.hourEstimate = hourEstimate;
    }


}
