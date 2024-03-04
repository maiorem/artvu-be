package com.art.api.scheduler.application.jobconfig.list;

import lombok.AllArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.List;

@AllArgsConstructor
public class ArtListItemWriter<T> extends JpaItemWriter<List<T>> {

    private final JpaItemWriter<T> jpaItemWriter;


    @Override
    public void write(Chunk<? extends List<T>> items) {
        Chunk<T> collect = new Chunk<T>();
        for(List<T> list : items){
            collect.addAll(list);
        }

        jpaItemWriter.write(collect);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
    }
}
