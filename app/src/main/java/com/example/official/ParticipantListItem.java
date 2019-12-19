package com.example.official;

public class ParticipantListItem {

    private String name, email, id, college;

    public ParticipantListItem(String name, String email, String id, String college) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getCollege() {
        return college;
    }
}
