/*
 * This file was last modified at 2020.09.01 19:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabel.java
 * $Id$
 */

package su.svn.daybook.domain.model;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
@Table("")
public class TagLabel implements Serializable, DBEntry<Long> {
    static final long serialVersionUID = -102L;

    @Id
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column("tag")
    private String tag;
}
