package su.svn.daybook.domain.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.daybook.utils.SerializeUtil;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTO classes in domain.security")
public class TestDataSecurity {

    public static final AuthRequest AUTH_REQUEST_1 = AuthRequest.builder()
            .username("username1")
            .password("password1")
            .build();

    public static final AuthResponse AUTH_RESPONSE_1 = AuthResponse.builder()
            .user("user1")
            .token("token1")
            .build();

    public static final ProfileResponse PROFILE_RESPONSE_1 = ProfileResponse.builder()
            .user("user1")
            .build();

    public static final User USER_1 = User.builder()
            .username("username1")
            .password("password1")
            .roles(new ArrayList<>() {{ add(Role.ROLE_USER); }})
            .build();

    @Nested
    @DisplayName("Class AuthRequest")
    class AuthRequestTest {

        AuthRequest clone;

        AuthRequest test;

        @BeforeEach
        void createNew() {
            test = new AuthRequest();
        }

        @BeforeEach
        void cloneTest() {
            clone = SerializeUtil.clone(AUTH_REQUEST_1);
        }

        @Test
        void defaults() {
            assertThat(test).hasFieldOrPropertyWithValue("username", null);
            assertThat(test).hasFieldOrPropertyWithValue("password", null);
        }
    }

    @Nested
    @DisplayName("Class AuthResponse")
    class AuthResponseTest {

        AuthResponse clone;

        AuthResponse test;

        @BeforeEach
        void createNew() {
            test = new AuthResponse();
        }

        @BeforeEach
        void cloneTest() {
            clone = SerializeUtil.clone(AUTH_RESPONSE_1);
        }

        @Test
        void defaults() {
            assertThat(test).hasFieldOrPropertyWithValue("user", null);
            assertThat(test).hasFieldOrPropertyWithValue("token", null);
        }
    }

    @Nested
    @DisplayName("Class ProfileResponse")
    class ProfileResponseTest {

        ProfileResponse clone;

        ProfileResponse test;

        @BeforeEach
        void createNew() {
            test = new ProfileResponse();
        }

        @BeforeEach
        void cloneTest() {
            clone = SerializeUtil.clone(PROFILE_RESPONSE_1);
        }

        @Test
        void defaults() {
            assertThat(test).hasFieldOrPropertyWithValue("user", null);
        }
    }

    @Nested
    @DisplayName("Class User")
    class UserTest {

        User clone;

        User test;

        @BeforeEach
        void createNew() {
            test = new User();
        }

        @BeforeEach
        void cloneTest() {
            clone = SerializeUtil.clone(USER_1);
        }

        @Test
        void defaults() {
            assertThat(test).hasFieldOrPropertyWithValue("username", null);
            assertThat(test).hasFieldOrPropertyWithValue("password", null);
        }
    }
}
