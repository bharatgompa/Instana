package com.bharat.instana.Repository;


import com.bharat.instana.util.Applications;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

//This repository is created for application class
public interface Repository extends ReactiveMongoRepository<Applications,Integer>
{
    Flux<Applications> findByPage(int page);
    Flux<Applications> findByItemsLabel(String label);
    Flux<Applications> findByPageSize(Integer label);
    //Mono<? extends Applications> save(Applications applications);
}