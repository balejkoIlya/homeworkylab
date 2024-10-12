import com.balejko.ylab.habittracker.models.Habit;
import com.balejko.ylab.habittracker.models.HabitLog;
import com.balejko.ylab.habittracker.models.User;
import com.balejko.ylab.habittracker.services.HabitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class HabitServiceTest {

    private HabitService habitService;
    private User user;

    @BeforeEach
    void setUp() {
        habitService = new HabitService();
        user = new User("Test", "test@mail.ru", "qwerty");
    }

    @Test
    void shouldAddHabitSuccessfully() {
        habitService.createHabit(user, "Привычка", "Какая-то привычка", "Каждый день");

        assertThat(user.getHabits()).hasSize(1);
        assertThat(user.getHabits().get(0).getTitle()).isEqualTo("Привычка");
    }

    @Test
    void shouldEditHabitSuccessfully() {
        habitService.createHabit(user, "Привычка", "Какая-то привычка", "Ежедневно");
        habitService.updateHabit(user, 0, "Утренние упражнения", "Утренние упражнения ежедневно", "Ежедневно");

        assertThat(user.getHabits().get(0).getTitle()).isEqualTo("Утренние упражнения");
        assertThat(user.getHabits().get(0).getDescription()).isEqualTo("Утренние упражнения ежедневно");
    }

    @Test
    void shouldDeleteHabitSuccessfully() {
        habitService.createHabit(user, "Exercise", "Exercise daily", "Daily");
        habitService.deleteHabit(user, 0);

        assertThat(user.getHabits()).isEmpty();
    }

    @Test
    void shouldLogHabitCompletionSuccessfully() {
        habitService.createHabit(user, "Exercise", "Exercise daily", "Daily");
        habitService.completeHabit(user, 0);

        assertThat(user.getHabits().get(0).getLogs()).hasSize(1);
        assertThat(user.getHabits().get(0).getLogs().get(0).isCompleted()).isTrue();
    }

    @Test
    void shouldCalculateStreakSuccessfully() {
        habitService.createHabit(user, "Exercise", "Exercise daily", "Daily");
        Habit habit = user.getHabits().get(0);

        habit.getLogs().add(new HabitLog(LocalDate.now().minusDays(2), true));
        habit.getLogs().add(new HabitLog(LocalDate.now().minusDays(1), true));
        habit.getLogs().add(new HabitLog(LocalDate.now(), true));

        int streak = habitService.calculateStreak(user, 0);

        assertThat(streak).isEqualTo(3);
    }




}

