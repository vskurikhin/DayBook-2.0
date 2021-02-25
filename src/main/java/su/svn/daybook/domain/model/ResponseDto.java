/*
 * This file was last modified at 2021.02.25 16:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ResponseDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 8037627863465759951L;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String message;

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResponseDto)) return false;
        final ResponseDto other = (ResponseDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$status = this.status;
        final Object other$status = other.status;
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$message = this.message;
        final Object other$message = other.message;
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $status = this.status;
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $message = this.message;
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }
}
