/*
 * This file was last modified at 2021.02.25 16:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * GenericExecuteSpec.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db.custom;

import lombok.Builder;
import org.springframework.data.r2dbc.core.DatabaseClient;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;

@Builder(builderClassName = "Builder")
public class GenericExecuteSpec<T> {

    private final DatabaseClient.GenericExecuteSpec execSpec;
    private final Supplier<?> defaultSupplier;
    private final Supplier<?> supplier;
    private final Class<?> tClass;
    private final String field;

    public GenericExecuteSpec(
            @Nonnull DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> defaultSupplier,
            @Nonnull Supplier<?> supplier,
            @Nonnull Class<?> tClass,
            @Nonnull String field) {
        this.execSpec = execSpec;
        this.defaultSupplier = defaultSupplier;
        this.supplier = supplier;
        this.tClass = tClass;
        this.field = field;
    }

    public static DatabaseClient.GenericExecuteSpec setBoolean(
            DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> supplier,
            boolean defaultValue,
            String field) {
        return GenericExecuteSpec.builder()
                .execSpec(execSpec)
                .field(field)
                .supplier(supplier)
                .defaultSupplier(() -> defaultValue)
                .tClass(Boolean.class)
                .build().get();
    }

    public static DatabaseClient.GenericExecuteSpec setInteger(
            DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> supplier,
            String field) {
        return GenericExecuteSpec.builder()
                .execSpec(execSpec)
                .field(field)
                .supplier(supplier)
                .tClass(Integer.class)
                .build().get();
    }

    public static DatabaseClient.GenericExecuteSpec setLocalDateTime(
            DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> defaultSupplier,
            Supplier<?> supplier,
            String field) {
        return GenericExecuteSpec.builder()
                .execSpec(execSpec)
                .field(field)
                .supplier(supplier)
                .defaultSupplier(LocalDateTime::now)
                .tClass(LocalDateTime.class)
                .build().get();
    }

    public static DatabaseClient.GenericExecuteSpec setLocalDateTimeNow(
            DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> supplier,
            String field) {
        return setLocalDateTime(execSpec, LocalDateTime::now, supplier, field);
    }

    public static DatabaseClient.GenericExecuteSpec setString(
            DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> supplier,
            String field) {
        return GenericExecuteSpec.builder()
                .execSpec(execSpec)
                .field(field)
                .supplier(supplier)
                .tClass(String.class)
                .build().get();
    }

    public static DatabaseClient.GenericExecuteSpec setUuid(
            DatabaseClient.GenericExecuteSpec execSpec,
            Supplier<?> supplier,
            String field) {
        return GenericExecuteSpec.builder()
                .execSpec(execSpec)
                .field(field)
                .supplier(supplier)
                .tClass(UUID.class)
                .build().get();
    }

    public DatabaseClient.GenericExecuteSpec get() {
        if (supplier.get() != null)
            return setValue(this.supplier);
        else
            return defaultSupplier != null ? setValue(defaultSupplier) : setNull();
    }

    @Nonnull
    private DatabaseClient.GenericExecuteSpec setValue(Supplier<?> supplier) {
        return execSpec.bind(field, supplier.get());
    }

    @Nonnull
    private DatabaseClient.GenericExecuteSpec setNull() {
        return execSpec.bindNull(field, tClass);
    }

}
