package su.svn.daybook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import su.svn.daybook.domain.security.TestDataSecurity;
import su.svn.daybook.services.security.JWTUtil;
import su.svn.daybook.services.security.PBKDF2Encoder;

import java.util.Date;

@SpringBootTest
public class SpringBootUnitsTest {

    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtZTEiLCJpYXQiOjE1OTg5NjU2MTIsImV4cCI6" +
            "MTU5ODk5NDQxMn0.GbhnhuEtscd4FymYDB0O1RAwFgxX7pnLfQLT9Jcrxa_TIZhsV2UhZjwkWDSw5Ygx-jNHrkqMOYd3OE2EApWzLA";

    public static final String ENCODED = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    PBKDF2Encoder pbkdf2Encoder;

    @Nested
    class JWTUtilTest {

        @Test
        void getUsernameFromToken() {
            Assertions.assertEquals("username1", jwtUtil.getUsernameFromToken(TOKEN));
        }

        @Test
        void getExpirationDateFromToken() {
            Assertions.assertEquals(new Date(1598994412000L), jwtUtil.getExpirationDateFromToken(TOKEN));
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
}
