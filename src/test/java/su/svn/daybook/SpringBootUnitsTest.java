package su.svn.daybook;

import org.junit.jupiter.api.Assertions;
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

    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZTEiLCJpYXQiOjE1OTg5NjU2MTIsImV4cCI6" +
            "MTU5ODk5NDQxMn0.GbhnhuEtscd4FymYDB0O1RAwFgxX7pnLfQLT9Jcrxa_TIZhsV2UhZjwkWDSw5Ygx-jNHrkqMOYd3OE2EApWzLA";

    public static final String ENCODED = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    PBKDF2Encoder pbkdf2Encoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Nested
    class JWTUtilTest {

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
        void generateToken() {
            Assertions.assertNotNull(TOKEN, jwtUtil.generateToken(TestDataSecurity.USER_1));
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

        public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJzdWIiOiJ1c2VyIiwiaWF0" +
                "IjoxNTk4OTY3OTE4LCJleHAiOjE1OTg5OTY3MTh9.yB0yNZ2dF-chit7SPEnV0FjCa7fPXuO49ilcnkV6sWYSEl9plxlaZTD3tRZ" +
                "UyrIaPOlEyokd_ascIdP-iSP8DQ";

        @Test
        void authenticate() {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(TOKEN, TOKEN);
            Mono<Authentication> test = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication expected = new UsernamePasswordAuthenticationToken("user", null, authorities);
            Assertions.assertEquals(expected, test.block());
        }
    }
}
