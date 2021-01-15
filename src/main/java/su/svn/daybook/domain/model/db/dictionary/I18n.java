/*
 * This file was last modified at 2021.01.15 01:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * I18n.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBLongEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("dictionary.i18")
public class I18n implements Serializable, DBLongEntry {

    private static final long serialVersionUID = 6450209380865072838L;

    @Id
    @Getter
    @Setter
    @Column("id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column("language_id")
    private Long languageId;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("message")
    private String message;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("translation")
    private String translation;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("user_name")
    private String userName;

    @Getter
    @NotNull
    @Column("create_time")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @Column("update_time")
    private LocalDateTime updateTime;

    @Getter
    @Setter
    @Column("enabled")
    private Boolean enabled;

    @Getter
    @Setter
    @Column("visible")
    private Boolean visible;

    @Getter
    @Setter
    @Column("flags")
    private Integer flags;

    @Override
    public Class<? extends DBLongEntry> getEClass() {
        return I18n.class;
    }

    @Override
    public String toString() {
        return "I18n{" +
                "id=" + id +
                ", languageId=" + languageId +
                ", message='" + message + '\'' +
                ", translation='" + translation + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", enabled=" + enabled +
                ", visible=" + visible +
                ", flags=" + flags +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof I18n)) return false;
        final I18n other = (I18n) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$languageId = this.languageId;
        final Object other$languageId = other.languageId;
        if (this$languageId == null ? other$languageId != null : !this$languageId.equals(other$languageId))
            return false;
        final Object this$message = this.message;
        final Object other$message = other.message;
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$translation = this.translation;
        final Object other$translation = other.translation;
        if (this$translation == null ? other$translation != null : !this$translation.equals(other$translation))
            return false;
        final Object this$userName = this.userName;
        final Object other$userName = other.userName;
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$createTime = this.createTime;
        final Object other$createTime = other.createTime;
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
        final Object this$updateTime = this.updateTime;
        final Object other$updateTime = other.updateTime;
        if (this$updateTime == null ? other$updateTime != null : !this$updateTime.equals(other$updateTime))
            return false;
        final Object this$enabled = this.enabled;
        final Object other$enabled = other.enabled;
        if (this$enabled == null ? other$enabled != null : !this$enabled.equals(other$enabled)) return false;
        final Object this$visible = this.visible;
        final Object other$visible = other.visible;
        if (this$visible == null ? other$visible != null : !this$visible.equals(other$visible)) return false;
        final Object this$flags = this.flags;
        final Object other$flags = other.flags;
        if (this$flags == null ? other$flags != null : !this$flags.equals(other$flags)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof I18n;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $languageId = this.languageId;
        result = result * PRIME + ($languageId == null ? 43 : $languageId.hashCode());
        final Object $message = this.message;
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $translation = this.translation;
        result = result * PRIME + ($translation == null ? 43 : $translation.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $createTime = this.createTime;
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $updateTime = this.updateTime;
        result = result * PRIME + ($updateTime == null ? 43 : $updateTime.hashCode());
        final Object $enabled = this.enabled;
        result = result * PRIME + ($enabled == null ? 43 : $enabled.hashCode());
        final Object $visible = this.visible;
        result = result * PRIME + ($visible == null ? 43 : $visible.hashCode());
        final Object $flags = this.flags;
        result = result * PRIME + ($flags == null ? 43 : $flags.hashCode());
        return result;
    }
}
