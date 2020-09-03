package su.svn.daybook.domain.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Function {

    public static final long UPPER_BOUND = 9_999_999_999_999_999L;

    public static final long MAX_BOUND = 9_223_372_036_854_775_807L;

    public static boolean isPrime(int value) {
        return new java.math.BigInteger(String.valueOf(value)).isProbablePrime(100);
    }

    public static String nextValueTagLabelSequence(java.sql.Connection conn) throws SQLException {
        //noinspection SqlResolve
        String sql = "SELECT NEXT VALUE FOR dictionary.tag_label_seq AS NEXT_ID FROM dual";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet results = ps.executeQuery();
        if (results.next()) {
            long result = results.getLong("NEXT_ID");
            if (0 > result || result > UPPER_BOUND) return null;
            return String.format("%016d", result);
        }
        return null;
    }
}
