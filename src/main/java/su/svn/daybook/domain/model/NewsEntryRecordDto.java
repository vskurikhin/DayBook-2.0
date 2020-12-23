/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryRecordDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;


import lombok.*;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class NewsEntryRecordDto {

    @Getter
    @Setter
    private Record record;

    @Getter
    @Setter
    private NewsEntry newsEntry;

    @Override
    public String toString() {
        return "NewsEntryRecordDto{" +
                "record=" + record +
                ", newsEntry=" + newsEntry +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof NewsEntryRecordDto)) return false;
        final NewsEntryRecordDto other = (NewsEntryRecordDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$record = this.record;
        final Object other$record = other.record;
        if (this$record == null ? other$record != null : !this$record.equals(other$record)) return false;
        final Object this$newsEntry = this.newsEntry;
        final Object other$newsEntry = other.newsEntry;
        if (this$newsEntry == null ? other$newsEntry != null : !this$newsEntry.equals(other$newsEntry)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NewsEntryRecordDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $record = this.record;
        result = result * PRIME + ($record == null ? 43 : $record.hashCode());
        final Object $newsEntry = this.newsEntry;
        result = result * PRIME + ($newsEntry == null ? 43 : $newsEntry.hashCode());
        return result;
    }
}
