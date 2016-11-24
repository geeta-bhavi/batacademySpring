/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.batacademy.domain;

/**
 *
 * @author swathi
 */
public class SelectedCoursesBean {

    private int courseID;
    private String courseName;
    private int a1;
    private int a2;
    private int a3;
    private String facultyName;
    private boolean completed;

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getA1() {
        return a1;
    }

    public void setA1(int A1) {
        this.a1 = A1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int A2) {
        this.a2 = A2;
    }

    public int getA3() {
        return a3;
    }

    public void setA3(int A3) {
        this.a3 = A3;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getCourseCompletedStatus() {
        if (this.completed) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
