package com.bharat.latencyfromdb.Controller;

import com.bharat.latencyfromdb.Repositories.MongoRepository;
import com.bharat.latencyfromdb.Utilities.Latency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class Controller
{
    @Autowired
    private MongoRepository repo;

    @GetMapping("latency")
    public Flux<List<List<List>>> getLatencyData()
    {
        Flux<Latency> latencyFlux = repo.findAll();
        Flux<List<List<List>>> map1 = latencyFlux
                        .map(a -> a.getItems().stream().map(b -> b.metrics)
                        .collect(Collectors.toList()).stream()
                        .map(c -> c.getLatencyp90()).collect(Collectors.toList()));
        return map1;
    }

    @GetMapping("/labels")
    public Flux<List<String>> getApplicationName()
    {
        Flux<Latency> all = repo.findAll();
        Flux<List<String>> labels = all.map(a -> a.getItems().stream().map(b -> b.application)
                                            .collect(Collectors.toList())
                                            .stream()
                                            .map(c -> c.label)
                                            .collect(Collectors.toList()));
        return labels;
    }
}
