package com.balejko.ylab.habittracker.models;

import java.time.LocalDate;

public class HabitLog {
    private LocalDate date;
    private boolean isCompleted;

    public HabitLog(LocalDate date,boolean isCompleted) {
        this.date=date;
        this.isCompleted = isCompleted;
    }

    public HabitLog() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
