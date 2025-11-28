package ek.dfofb.projektkalkulationsvaerktoej.model;

import java.util.List;

public class Role
{

    private int roleID;
    private String name;
    private String description;
    private List<Integer> permissionList;

    public Role(){}

    public Role(List<Integer> permissionList, String description, String name, int roleID)
    {
        this.permissionList = permissionList;
        this.description = description;
        this.name = name;
        this.roleID = roleID;
    }

    public int getRoleID()
    {
        return roleID;
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

    public List<Integer> getPermissionList()
    {
        return permissionList;
    }

    public void setPermissionList(List<Integer> permissionList)
    {
        this.permissionList = permissionList;
    }
}
