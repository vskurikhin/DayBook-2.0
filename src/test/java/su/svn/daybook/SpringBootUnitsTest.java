package su.svn.daybook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.security.TestDataSecurity;
import su.svn.daybook.services.security.AuthenticationManager;
import su.svn.daybook.services.security.JWTUtil;
import su.svn.daybook.services.security.PBKDF2Encoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SpringBootUnitsTest {

   public static final String ENCODED = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    PBKDF2Encoder pbkdf2Encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Nested
    class JWTUtilTest {

        public String TOKEN;

        @BeforeEach
        void generateToken() {
            TOKEN = jwtUtil.generateToken(TestDataSecurity.USER_1);
        }

        @Test
        void getUsernameFromToken() {
            Assertions.assertEquals("username1", jwtUtil.getUsernameFromToken(TOKEN));
        }

        @Test
        void getExpirationDateFromToken() {
            Assertions.assertTrue(jwtUtil.getExpirationDateFromToken(TOKEN).getTime() > 0L);
            Assertions.assertTrue(jwtUtil.getExpirationDateFromToken(TOKEN).getTime() < 1999999999999L);
        }

        @Test
        void validateToken() {
            Assertions.assertTrue(jwtUtil.validateToken(TOKEN));
        }
    }

    @Nested
    class PBKDF2EncoderTest {

        @Test
        void encode() {
            Assertions.assertEquals(ENCODED, pbkdf2Encoder.encode("user"));
        }

        @Test
        void matches() {
            Assertions.assertTrue(pbkdf2Encoder.matches("user", ENCODED));
            Assertions.assertFalse(pbkdf2Encoder.matches("abc", ENCODED));
        }
    }

    @Nested
    class AuthenticationManagerTest {

        public String TOKEN;

        @BeforeEach
        void generateToken() {
            TOKEN = jwtUtil.generateToken(TestDataSecurity.USER_1);
        }

        @Test
        void authenticate() {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(TOKEN, TOKEN);
            Mono<Authentication> test = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication expected = new UsernamePasswordAuthenticationToken(TestDataSecurity.USER_1.getUsername(), null, authorities);
            Assertions.assertEquals(expected, test.block());
        }
    }
}
