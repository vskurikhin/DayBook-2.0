/*
 * This file was last modified at 2021.02.03 18:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ProfileResponse.java
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
public class ProfileResponse implements Serializable {
    private static final long serialVersionUID = 7417220763765443627L;
    private String user;
}
