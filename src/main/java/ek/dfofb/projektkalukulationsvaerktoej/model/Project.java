package ek.dfofb.projektkalukulationsvaerktoej.model;

import java.util.List;

public class Project
{
    private String title;
    private String deadline;
    private List<Boolean> subtasks;


    public String getTitle() {
        return title;
    }

    public String getDeadline() {
        return deadline;
    }

    public List<Boolean> getSubtasks() {
        return subtasks;
    }

    public Project(String title, String deadline, List<Boolean> subtasks) {
        this.title = title;
        this.deadline = deadline;
        this.subtasks = subtasks;
    }
}
