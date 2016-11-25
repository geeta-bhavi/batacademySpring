package com.project.batacademy.domain;

public class RegisteredCourses implements java.io.Serializable {

    private RegisteredCoursesId id;
    private String courseName;
    private boolean completed;

    public RegisteredCourses() {
    }

    public RegisteredCourses(RegisteredCoursesId id, String courseName, boolean completed) {
        this.id = id;
        this.courseName = courseName;
        this.completed = completed;
    }

    public RegisteredCoursesId getId() {
        return this.id;
    }

    public void setId(RegisteredCoursesId id) {
        this.id = id;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
