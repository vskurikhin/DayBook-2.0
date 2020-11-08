package su.svn.daybook.domain.dao.db.db;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsEntry;

public interface NewsEntryCustomizedDao {

    Mono<NewsEntry> insert(NewsEntry newsEntry);

    Flux<NewsEntry> insertAll(Iterable<NewsEntry> entries);

    Mono<NewsEntry> transactionalInsert(NewsEntry newsEntry);

    Flux<NewsEntry> transactionalInsertAll(Iterable<NewsEntry> entries);
}
