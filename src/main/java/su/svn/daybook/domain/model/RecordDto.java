/*
 * This file was last modified at 2021.02.24 19:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import su.svn.daybook.domain.model.db.db.Record;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class RecordDto<T extends DBUuidEntry> implements Serializable {

    private static final long serialVersionUID = -2469972395793373903L;

    @Getter
    @Setter
    private Record record;

    @Getter
    @Setter
    private T entity;

    public RecordDto(Record record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "RecordDto{" +
                "record=" + record +
                ", entity=" + entity +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RecordDto)) return false;
        final RecordDto<?> other = (RecordDto<?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$record = this.record;
        final Object other$record = other.record;
        if (this$record == null ? other$record != null : !this$record.equals(other$record)) return false;
        final Object this$entity = this.entity;
        final Object other$entity = other.entity;
        if (this$entity == null ? other$entity != null : !this$entity.equals(other$entity)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RecordDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $record = this.record;
        result = result * PRIME + ($record == null ? 43 : $record.hashCode());
        final Object $entity = this.entity;
        result = result * PRIME + ($entity == null ? 43 : $entity.hashCode());
        return result;
    }
}
