/*
 * This file was last modified at 2020.08.27 08:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AuthResponse.java
 * $Id$
 */

package su.svn.daybook.domain.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponse implements Serializable {
    private String user;
    private String token;
}
