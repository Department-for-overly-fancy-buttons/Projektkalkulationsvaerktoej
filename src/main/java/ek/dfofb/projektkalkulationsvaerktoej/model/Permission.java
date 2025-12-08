package ek.dfofb.projektkalkulationsvaerktoej.model;

public enum Permission
{
    VIEW_PROJECT(1),
    ADD_TASKS(2),
    EDIT_TASKS(3),
    DELETE_TASKS(4),
    ADD_PROJECTS(5),
    EDIT_PROJECTS(6),
    DELETE_PROJECTS(7),
    GRANT_PERMISSIONS(8);

    private final int id;

    Permission(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public static Permission fromId(int id)
    {
        for (Permission p : values())
        {
            if (p.id == id) return p;
        }
        throw new IllegalArgumentException("Unknown permission id: " + id);
    }


}
