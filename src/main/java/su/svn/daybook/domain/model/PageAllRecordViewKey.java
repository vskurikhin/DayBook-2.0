/*
 * This file was last modified at 2021.03.06 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * PageAllRecordViewKey.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;

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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PageAllRecordViewKey)) return false;
        final PageAllRecordViewKey other = (PageAllRecordViewKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$pageable = this.pageable;
        final Object other$pageable = other.pageable;
        if (this$pageable == null ? other$pageable != null : !this$pageable.equals(other$pageable)) return false;
        if (this.totalCount != other.totalCount) return false;
        if (this.size != other.size) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageAllRecordViewKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $pageable = this.pageable;
        result = result * PRIME + ($pageable == null ? 43 : $pageable.hashCode());
        final long $totalCount = this.totalCount;
        result = result * PRIME + (int) ($totalCount >>> 32 ^ $totalCount);
        result = result * PRIME + this.size;
        return result;
    }
}
