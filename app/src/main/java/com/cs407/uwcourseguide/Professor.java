package com.cs407.uwcourseguide;

public class Professor {
    private String name;
    private String department;
    private String school;
    private String ratings;
    private String likeliness;
    private String totalRatings;

    public Professor (String name, String department, String school, String ratings, String likeliness, String totalRatings) {
        this.name = name;
        this.department = department;
        this.school = school;
        this.ratings = ratings;
        this.likeliness = likeliness;
        this.totalRatings = totalRatings;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getSchool() {
        return school;
    }

    public String getRatings() {
        return ratings;
    }

    public String getLikeliness() {
        return likeliness;
    }

    public String getTotalRatings() {
        return totalRatings;
    }
}
