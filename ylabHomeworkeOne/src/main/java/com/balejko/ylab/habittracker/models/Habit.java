package com.balejko.ylab.habittracker.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habit {
    private String title;
    private String description;
    private String frequency;
    private LocalDate createdAt;
    private List<HabitLog> logs;

    public Habit(String title, String description, String frequency) {
        this.title = title;
        this.description = description;
        this.frequency = frequency;
        this.createdAt=LocalDate.now();
        logs=new ArrayList<>();
    }

    public void habitCompletion(){
        logs.add(new HabitLog(LocalDate.now(),true));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public List<HabitLog> getLogs() {
        return logs;
    }

    public void setLogs(List<HabitLog> logs) {
        this.logs = logs;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
