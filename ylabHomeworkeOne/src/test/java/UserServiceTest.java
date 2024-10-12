import com.balejko.ylab.habittracker.models.User;
import com.balejko.ylab.habittracker.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        userService.registerUser("test@mail.ru", "qwerty", "Test");
        assertThat(userService.userExists("test@mail.ru")).isTrue();
    }

    @Test
    void shouldAuthenticateSuccessfully() {
        userService.registerUser("test@mail.ru", "qwerty", "Test");
        User user = userService.login("test@mail.ru", "qwerty");
        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("test@mail.ru");
    }

    @Test
    void shouldFailAuthenticationWithWrongPassword() {
        userService.registerUser("test@mail.ru", "qwerty", "Test");
        User user = userService.login("test@mail.ru", "wrongpassword");

        assertThat(user).isNull();
    }

    @Test
    void shouldEditProfileSuccessfully() {
        userService.registerUser("test@mail.ru", "qwerty", "Test");
        User user = userService.login("test@mail.ru", "qwerty");

        userService.editProfile(user, "newTest", "newpassword123", "test2@mail.ru");

        assertThat(userService.userExists("test@mail.ru")).isFalse();
        assertThat(userService.userExists("test2@mail.ru")).isTrue();
        assertThat(user.getName()).isEqualTo("newTest");
        assertThat(user.getPassword()).isEqualTo("newpassword123");
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        userService.registerUser("test@mail.ru", "qwerty", "Test");
        User user = userService.login("test@mail.ru", "qwerty");

        userService.deleteUser(user);

        assertThat(userService.userExists("test@mail.ru")).isFalse();
    }
}
