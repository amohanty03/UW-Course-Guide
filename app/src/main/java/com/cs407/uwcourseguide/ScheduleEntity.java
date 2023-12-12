package com.cs407.uwcourseguide;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ScheduleEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String className;
    public String professor;
    public String location;
    public String roomNumber;
    public String time;
    public String days;

    // Constructors
    public ScheduleEntity() {}

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        return days;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
