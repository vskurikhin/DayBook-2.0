/*
 * This file was last modified at 2021.02.25 19:38 by Victor N. Skurikhin.
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
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
    public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 8037627863465759951L;

    @Getter
    @Setter
    private String error;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String requestId;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private Date timestamp;

    @Override
    public String toString() {
        return "ResponseDto{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                ", requestId='" + requestId + '\'' +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResponseDto)) return false;
        final ResponseDto other = (ResponseDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$error = this.error;
        final Object other$error = other.error;
        if (this$error == null ? other$error != null : !this$error.equals(other$error)) return false;
        final Object this$message = this.message;
        final Object other$message = other.message;
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$path = this.path;
        final Object other$path = other.path;
        if (this$path == null ? other$path != null : !this$path.equals(other$path)) return false;
        final Object this$requestId = this.requestId;
        final Object other$requestId = other.requestId;
        if (this$requestId == null ? other$requestId != null : !this$requestId.equals(other$requestId)) return false;
        final Object this$status = this.status;
        final Object other$status = other.status;
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$timestamp = this.timestamp;
        final Object other$timestamp = other.timestamp;
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $error = this.error;
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        final Object $message = this.message;
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $path = this.path;
        result = result * PRIME + ($path == null ? 43 : $path.hashCode());
        final Object $requestId = this.requestId;
        result = result * PRIME + ($requestId == null ? 43 : $requestId.hashCode());
        final Object $status = this.status;
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $timestamp = this.timestamp;
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        return result;
    }
}
