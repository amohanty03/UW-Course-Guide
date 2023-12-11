package com.cs407.uwcourseguide;

public class Course {
    private String courseTitle;
    private String courseDescription;
    private String cGPA;
    private String credits;
    private String requisites;
    private String courseDesignation;
    private String repeatCredit;
    private String lastTaught;
    private String crossList;
    private String title;

    public Course(String courseTitle, String courseDescription, String cGPA, String credits,
                  String requisites, String courseDesignation, String repeatCredit,
                  String lastTaught, String crossList, String title) {
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.cGPA = cGPA;
        this.credits = credits;
        this.requisites = requisites;
        this.courseDesignation = courseDesignation;
        this.repeatCredit = repeatCredit;
        this.lastTaught = lastTaught;
        this.crossList = crossList;
        this.title = title;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public String getcGPA() {
        return cGPA;
    }

    public String getCredits() {
        return credits;
    }

    public String getRequisites() {
        return requisites;
    }

    public String getCourseDesignation() {
        return courseDesignation;
    }

    public String getRepeatCredit() {
        return repeatCredit;
    }

    public String getLastTaught() {
        return lastTaught;
    }

    public String getCrossList() {
        return crossList;
    }

    public String getTitle() {
        return title;
    }
}
