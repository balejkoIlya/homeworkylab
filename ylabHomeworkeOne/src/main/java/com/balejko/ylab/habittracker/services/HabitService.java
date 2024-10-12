package com.balejko.ylab.habittracker.services;

import com.balejko.ylab.habittracker.models.Habit;
import com.balejko.ylab.habittracker.models.HabitLog;
import com.balejko.ylab.habittracker.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class HabitService {

    public void createHabit(User user, String title, String description, String frequency) {
        Habit habit = new Habit(title, description, frequency);
        user.getHabits().add(habit);
    }

    public void deleteHabit(User user,int habitIndex){
        if(habitIndex>=0&&habitIndex<user.getHabits().size()){
            Habit deletedHabit=user.getHabits().remove(habitIndex);
            System.out.println("Привычка "+deletedHabit.getTitle()+" успешно удалена!");
        } else {
            System.out.println("Неверный индекс");
        }
    }

    public void updateHabit(User user,int habitIndex, String newTitle, String newDescription,String newFrequency){
        if(habitIndex>=0&&habitIndex<user.getHabits().size()){
            Habit habit=user.getHabits().get(habitIndex);
            habit.setTitle(newTitle);
            habit.setDescription(newDescription);
            habit.setFrequency(newTitle);
        } else{
            System.out.println("Неверный индекс");
        }
    }

    public void viewHabits(User user, String filter) {
        List<Habit> habits = user.getHabits();
        if (habits.isEmpty()) {
            System.out.println("У вас пока нет привычек!");
            return;
        }

        List<Habit> filteredHabits = habits;
        if ("completed".equalsIgnoreCase(filter)) {
            filteredHabits.stream()
                    .filter(habit -> habit.getLogs().stream().anyMatch(log -> log.isCompleted()))
                    .collect(Collectors.toList());
        } else if ("not completed".equalsIgnoreCase(filter)) {
            filteredHabits.stream()
                    .filter(habit -> habit.getLogs().stream().anyMatch(log -> !log.isCompleted()))
                    .collect(Collectors.toList());
        }
        System.out.println("Ваши привычки:");
        for (int i = 0; i < habits.size(); i++) {
            System.out.println((i + 1) + ")" + habits.get(i).getTitle());
        }
    }


    public void completeHabit(User user, int habitIndex) {
        List<Habit> habits = user.getHabits();
        if (habitIndex >= 0 && habitIndex < habits.size()) {
            Habit habit = habits.get(habitIndex);
            habit.habitCompletion();
            System.out.println("Выполнение привычки отмечено!");
        } else {
            System.out.println("Неверный индекс привычки");
        }
    }

    public void generateStatistics(User user, int habitIndex, LocalDate startDate, LocalDate endDate) {
        List<Habit> habits = user.getHabits();
        if (habitIndex >= 0 && habitIndex < habits.size()) {
            Habit habit = habits.get(habitIndex);
            List<HabitLog> logsInPeriod = habit.getLogs().stream()
                    .filter(log -> log.getDate().isAfter(startDate) && log.getDate().isBefore(endDate))
                    .collect(Collectors.toList());
            long completedCount = logsInPeriod.stream().filter(HabitLog::isCompleted).count();
            long totalCount = logsInPeriod.size();
            System.out.println("Статистика выполнения привычки '" + habit.getTitle() + "' за период:");
            System.out.println("Количество выполнений: " + completedCount);
            System.out.println("Общее количество дней в периоде: " + totalCount);
            if (totalCount > 0) {
                System.out.println("Процент выполнения: " + (completedCount * 100 / totalCount) + "%");
            }
        } else {
            System.out.println("Неверный индекс привычки.");
        }
    }

    public int calculateStreak(User user, int habitIndex) {
        List<Habit> habits = user.getHabits();
        if (habitIndex >= 0 && habitIndex < habits.size()) {
            Habit habit = habits.get(habitIndex);
            List<HabitLog> logs = habit.getLogs();
            int streak = 0;
            LocalDate currentDate = LocalDate.now();
            for (int i = logs.size() - 1; i >= 0; i--) {
                HabitLog log = logs.get(i);
                if (log.isCompleted() && log.getDate().equals(currentDate)) {
                    streak++;
                    currentDate = currentDate.minusDays(1);
                } else {
                    break;
                }
            }

            System.out.println("Текущая серия выполнения привычки '" + habit.getTitle() + "' составляет " + streak + " дней.");
            return streak;
        } else {
            System.out.println("Неверный индекс привычки.");
            return 0;
        }
    }

    public void generateProgressReport(User user) {
        System.out.println("Отчет по прогрессу выполнения привычек для пользователя: " + user.getName());
        for (Habit habit : user.getHabits()) {
            int streak = calculateStreak(user, user.getHabits().indexOf(habit));
            List<HabitLog> logs = habit.getLogs();
            long completedCount = logs.stream().filter(HabitLog::isCompleted).count();
            long totalCount = logs.size();

            System.out.println("Привычка: " + habit.getTitle());
            System.out.println("Количество выполнений: " + completedCount);
            System.out.println("Общее количество дней отслеживания: " + totalCount);
            if (totalCount > 0) {
                System.out.println("Процент выполнения: " + (completedCount * 100 / totalCount) + "%");
            }
            System.out.println("Текущая серия выполнения (streak): " + streak + " дней.");
            System.out.println("---------------------------------");
        }
    }

}
