/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * PageAllRecordViewKey.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Objects;

public class PageAllRecordViewKey implements Serializable {

    private static final long serialVersionUID = 6735287828001150076L;

    @Getter
    private final Pageable pageable;

    @Getter
    private final long totalCount;

    @Getter
    private final int size;

    public PageAllRecordViewKey(Pageable pageable, long totalCount, int size) {
        this.pageable = pageable;
        this.totalCount = totalCount;
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageAllRecordViewKey{" +
                "pageable=" + pageable +
                ", totalCount=" + totalCount +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageAllRecordViewKey that = (PageAllRecordViewKey) o;
        return totalCount == that.totalCount &&
                size == that.size &&
                Objects.equals(pageable, that.pageable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageable, totalCount, size);
    }
}
