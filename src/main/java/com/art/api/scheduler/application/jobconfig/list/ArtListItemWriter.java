package com.art.api.scheduler.application.jobconfig.list;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class ArtListItemWriter<T> extends JpaItemWriter<List<T>> {

    private final JpaItemWriter<T> jpaItemWriter;

    @Override
    public void write(Chunk<? extends List<T>> items) {
        super.write(items);
    }
}
