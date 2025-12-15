package ek.dfofb.projektkalkulationsvaerktoej.model;

import java.sql.Date;

public class Project {
    private int projectID;
    private String name;
    private String description;
    private boolean isActive;
    private int hourEstimate; // Jeg tænker vi ønsker at beregne denne ud fra tasks, så venter lige til den er færdig
    private Date startDate;
    private Date deadline;
    private int hoursRemaining;
    private int hoursSpentOnProject;

    public Project() {

    }

    public Project(int projectID, String name, String description, boolean isActive, Date startDate, Date deadline) {
        this.projectID = projectID;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.startDate = startDate;
        this.deadline = deadline;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getHourEstimate() {
        return hourEstimate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setHourEstimate(int hourEstimate) {
        this.hourEstimate = hourEstimate;
    }

    public int getHoursRemaining() {
        return hoursRemaining;
    }

    public void setHoursRemaining(int hoursRemaining) {
        this.hoursRemaining = hoursRemaining;
    }

    public int getHoursSpentOnProject() {
        return hoursSpentOnProject;
    }

    public void setHoursSpentOnProject(int hoursSpentOnProject) {
        this.hoursSpentOnProject = hoursSpentOnProject;
    }
}
