package su.svn.daybook.utils;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.test.StepVerifier;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestDatabaseUtil {

    private static File getTestClassDirectory(Class<?> aClass) {
        return new File(aClass.getResource("/META-INF/sql/" + aClass.getSimpleName()).getFile());
    }

    public static void constructTestDatabase(
            DatabaseClient databaseClient,
            TransactionalOperator transactionalOperator,
            Class<?> aClass) {
        File directory = getTestClassDirectory(aClass);
        Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isFile).sorted().forEach(file -> {
            String content = null;
            try {
                content = Files.readString(Path.of(file.getPath()), StandardCharsets.US_ASCII);
                executeTransactionalOperator(databaseClient, transactionalOperator, content);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("file = " + file);
                System.err.println("content = " + content);
            }
        });
    }

    public static void executeTransactionalOperator(
            DatabaseClient databaseClient,
            TransactionalOperator transactionalOperator,
            String sql) {
        try {
            transactionalOperator.execute(tx -> databaseClient.execute(sql).then().handle((aVoid, sink) -> {
                // TODO
            })).as(StepVerifier::create).verifyComplete();
        } catch (Exception e) {
            System.err.println("constructTestDatabase rollback");
        }
    }

    public static void dropTestDatabase(
            DatabaseClient databaseClient,
            TransactionalOperator transactionalOperator,
            Class<?> tClass) {
        try {
            String resourceNamePrefix = "/META-INF/sql/" + tClass.getSimpleName();
            InputStream is = tClass.getResourceAsStream(resourceNamePrefix + "_tearDown.sql");
            String content = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            executeTransactionalOperator(databaseClient, transactionalOperator, content);
        } catch (Exception e) {
            System.err.println("constructTestDatabase rollback");
        }
    }
}
