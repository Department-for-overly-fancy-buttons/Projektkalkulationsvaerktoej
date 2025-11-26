package ek.dfofb.projektkalkulationsvaerktoej.model;

import java.util.Date;
import java.util.Set;

public class Task {
    private int taskID;
    private String name;
    private String description;
    private int hourEstimate;
    private boolean isCompleted;
    private Date deadLine;
    private Date startDate;
    private int projectID;
    private int ParentID;
    private Set<Task> tasks;
    private Set<Account> assignedWorkers;

    public Task(int taskID, String name, String description, int hourEstimate,
                boolean isCompleted, Date deadLine, Date startDate, int projectID,
                int parentID) {
        this.taskID = taskID;
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
        this.hourEstimate = hourEstimate;
        this.deadLine = deadLine;
        this.startDate = startDate;
        this.projectID = projectID;
        ParentID = parentID;
    }

    public Task() {
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
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

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getHourEstimate() {
        return hourEstimate;
    }

    public void setHourEstimate(int hourEstimate) {
        this.hourEstimate = hourEstimate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<Account> getAssignedWorkers() {
        return assignedWorkers;
    }

    public void setAssignedWorkers(Set<Account> assignedWorkers) {
        this.assignedWorkers = assignedWorkers;
    }

}
