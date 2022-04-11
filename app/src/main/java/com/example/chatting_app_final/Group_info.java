package com.example.chatting_app_final;

public class Group_info {

    private String GroupName;
    private String GroupCode;

    public Group_info() {
    }

    public Group_info(String groupName, String groupCode) {
        GroupName = groupName;
        GroupCode = groupCode;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupCode() {
        return GroupCode;
    }

    public void setGroupCode(String groupCode) {
        GroupCode = groupCode;
    }
}
