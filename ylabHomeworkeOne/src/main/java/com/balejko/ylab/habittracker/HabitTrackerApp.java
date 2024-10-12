package com.balejko.ylab.habittracker;
import com.balejko.ylab.habittracker.models.User;
import com.balejko.ylab.habittracker.services.HabitService;
import com.balejko.ylab.habittracker.services.UserService;

import java.time.LocalDate;
import java.util.Scanner;

public class HabitTrackerApp {
    private static UserService userService = new UserService();
    private static HabitService habitService = new HabitService();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            if (loggedInUser == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showAuthMenu() {
        System.out.println("=== Меню авторизации ===");
        System.out.println("1. Регистрация");
        System.out.println("2. Вход");
        System.out.println("3. Выход");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                register();
                break;
            case "2":
                login();
                break;
            case "3":
                System.exit(0);
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static void register() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        userService.registerUser(email, password, name);
        System.out.println("Регистрация успешна!");
    }

    private static void login() {
        System.out.println("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        loggedInUser = userService.login(email, password);

        if (loggedInUser != null) {
            System.out.println("Вход выполнен успешно!");
        } else {
            System.out.println("Неверный email или пароль.");
        }
    }

    private static void showMainMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Просмотр привычек");
        System.out.println("2. Добавить привычку");
        System.out.println("3. Редактировать привычку");
        System.out.println("4. Удалить привычку");
        System.out.println("5. Отметить выполнение привычки");
        System.out.println("6. Просмотреть статистику по привычке");
        System.out.println("7. Редактировать профиль");
        System.out.println("8. Удалить аккаунт");
        System.out.println("9. Выйти из аккаунта");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                habitService.viewHabits(loggedInUser,"ALL");
                break;
            case "2":
                addHabit();
                break;
            case "3":
                editHabit();
                break;
            case "4":
                deleteHabit();
                break;
            case "5":
                logHabitCompletion();
                break;
            case "6":
                generateProgressReport();
                break;
            case "7":
                editProfile();
                break;
            case "8":
                deleteAccount();
                break;
            case "9":
                logout();
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static void addHabit() {
        System.out.println("Введите название привычки:");
        String title = scanner.nextLine();
        System.out.println("Введите описание привычки:");
        String description = scanner.nextLine();
        System.out.println("Укажите частоту выполнения (ежедневно, еженедельно):");
        String frequency = scanner.nextLine();
        habitService.createHabit(loggedInUser, title, description, frequency);
    }

    private static void editHabit() {
        habitService.viewHabits(loggedInUser,"ALL");
        System.out.println("Введите номер привычки для редактирования:");
        int habitIndex = Integer.parseInt(scanner.nextLine()) - 1;
        System.out.println("Введите новое название привычки:");
        String newTitle = scanner.nextLine();
        System.out.println("Введите новое описание привычки:");
        String newDescription = scanner.nextLine();
        System.out.println("Введите новую частоту выполнения (ежедневно, еженедельно):");
        String newFrequency = scanner.nextLine();
        habitService.updateHabit(loggedInUser, habitIndex, newTitle, newDescription, newFrequency);
    }

    private static void deleteHabit() {
        habitService.viewHabits(loggedInUser,"ALL");
        System.out.println("Введите номер привычки для удаления:");
        int habitIndex = Integer.parseInt(scanner.nextLine()) - 1;
        habitService.deleteHabit(loggedInUser, habitIndex);
    }

    private static void logHabitCompletion() {
        habitService.viewHabits(loggedInUser,"ALL");
        System.out.println("Введите номер привычки для логирования выполнения:");
        int habitIndex = Integer.parseInt(scanner.nextLine()) - 1;
        habitService.completeHabit(loggedInUser, habitIndex);
    }

    private static void generateProgressReport() {
        habitService.generateProgressReport(loggedInUser);
    }

    private static void editProfile() {
        System.out.println("Введите новое имя:");
        String newName = scanner.nextLine();
        System.out.println("Введите новый email:");
        String newEmail = scanner.nextLine();
        System.out.println("Введите новый пароль:");
        String newPassword = scanner.nextLine();
        userService.editProfile(loggedInUser, newName, newEmail, newPassword);
    }

    private static void deleteAccount() {
        System.out.println("Вы уверены, что хотите удалить аккаунт? (да/нет):");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("да")) {
            userService.deleteUser(loggedInUser);
            loggedInUser = null;
            System.out.println("Аккаунт удален.");
        }
    }

    private static void logout() {
        loggedInUser = null;
        System.out.println("Вы вышли из аккаунта.");
    }
}
