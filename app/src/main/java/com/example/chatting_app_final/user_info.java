package com.example.chatting_app_final;

import java.util.ArrayList;

public class user_info {

    private String username;
    private String user_id;
    private ArrayList<String> group_info;

    public user_info() {
        user_id="null";
        username="null";
        group_info=new ArrayList<String>();
        group_info.add("kit");
        group_info.add("it");
    }

    public user_info(String username, String user_id, ArrayList<String> group_info) {
        this.username = username;
        this.user_id = user_id;
        this.group_info = group_info;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public ArrayList<String> getGroup_info() {
        return group_info;
    }

    public void setGroup_info(ArrayList<String> group_info) {
        this.group_info = group_info;
    }
}
