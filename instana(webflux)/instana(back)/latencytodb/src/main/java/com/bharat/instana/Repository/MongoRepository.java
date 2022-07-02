package com.bharat.instana.Repository;

import com.bharat.instana.util.Latency;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

//This repository is created for latency class
public interface MongoRepository extends ReactiveMongoRepository<Latency,Integer>
{
    Flux<Latency> findByTotalHits(int th);

    @Query(value = "{},{items.metrics.latencyp90:1}")
    Flux<Latency.Item> findByItemsMetricsLatencyp90();
}
