package su.svn.daybook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import su.svn.daybook.services.security.JWTUtil;

import java.util.Date;

@SpringBootTest
public class SpringBootUnitsTest {

    public static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjpbIlJPTEVfVVNFUiJdLCJzdWIiOiJ1c2VyIiwiaWF0Ijox" +
            "NTk4OTYzOTg5LCJleHAiOjE1OTg5OTI3ODl9.z5rd_bvC7ZYFLfRBY6pr1jtMr7fRyQ-I0jj7GmRBl1sYd28caMrmxf1M1QTjyzV715o" +
            "Lwj6q95jMUl2Y6uHvYQ";

    @Autowired
    JWTUtil jwtUtil;

    @Nested
    class JWTUtilTest {

        @Test
        void getUsernameFromToken() {
            Assertions.assertEquals("user", jwtUtil.getUsernameFromToken(TOKEN));
        }

        @Test
        void getExpirationDateFromToken() {
            Assertions.assertEquals(new Date(1598992789000L), jwtUtil.getExpirationDateFromToken(TOKEN));
        }
    }
}
