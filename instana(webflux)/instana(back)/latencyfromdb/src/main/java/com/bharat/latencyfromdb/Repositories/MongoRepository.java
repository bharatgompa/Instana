package com.bharat.latencyfromdb.Repositories;

import com.bharat.latencyfromdb.Utilities.Latency;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MongoRepository extends ReactiveMongoRepository<Latency,Integer>
{

}

