package com.bharat.instana.controller;

import com.bharat.instana.Repository.MongoRepository;
import com.bharat.instana.Repository.Repository;
import com.bharat.instana.util.Applications;
import com.bharat.instana.util.Latency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@RestController
@CrossOrigin
public class Controller
{
    @Autowired
    private Repository repository;

    @Autowired
    private MongoRepository repo;

    private WebClient webClient;

    @PostConstruct
    public void init()
    {
        webClient = WebClient.builder().baseUrl("https://apm-turtlemint.instana.io/")
                .defaultHeader("authorization","apiToken hkkA6hNtSY2epZPWBaa3Gw")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
    //for application details
    @GetMapping(value="/applications",produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public  Mono<Applications> getApplications()
    {
        Mono<Applications> data = webClient.get()
                .uri("api/application-monitoring/applications")
                .retrieve()
                .bodyToMono(Applications.class);
        return data.flatMap(repository::save);
    }
    //for latency details
    @PostMapping(value="/latency",produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Latency> getLatency(@RequestBody Object objectMono)
    {
        Mono<Latency> info = webClient.post()
                .uri("api/application-monitoring/metrics/applications")
                .syncBody(objectMono)
                .retrieve()
                .bodyToMono(Latency.class);
        return info.flatMap(repo::save);
    }




    /*//getting data from database another API
    //pulling data from db
    //pulling metrics data from applications by page
    @GetMapping("/metrics/{page}")
    public Flux<Applications> getMetrics(@PathVariable("page") int page )
    {
        return repository.findByPage(page);
    }
    //pulling metrics data from latency by totalhits
    @GetMapping("/metrics/latency/{th}")
    public Flux<Latency> getTotalHits(@PathVariable("th") int th)
    {
        return repo.findByTotalHits(th);
    }
    //application class
    //pulling items.label data from applications by entering label name
    @GetMapping("application/{label}")
    public Flux<Applications> getApplicationData(@PathVariable String label)
    {
       Flux<Applications> byItemsLabel = repository.findByItemsLabel(label);
       return byItemsLabel;
    }
    //for retrieving latencyp90 data from latency collection in instanaflux database
    @GetMapping("latency")
    public Flux<List<List<List>>> getLatencyData()
    {
        Flux<Latency> latencyFlux = repo.findAll();
        Flux<List<List<List>>> map1 = latencyFlux.map(a -> a.getItems().stream().map(b -> b.metrics).collect(Collectors.toList()).stream().map(c -> c.getLatencyp90()).collect(Collectors.toList()));
        return map1;
        //Flux<List<Number[][]>> map = latencyFlux.map(a -> a.getItems().stream().map(b -> b.metrics).collect(Collectors.toList()).stream().map(c -> c.getLatencyp90()).collect(Collectors.toList()));
        //Flux<List<Long[][]>> map = latencyFlux.map(a -> a.getItems().stream().map(b -> b.metrics).collect(Collectors.toList()).stream().map(c -> c.getLatencyp90()).collect(Collectors.toList()));
        //return map;
        /*List<List<List<Long>>> collect = all.stream().flatMap(a -> a.getItems().stream().map(b -> b.metrics).map(c -> c.getLatencyp90())).collect(Collectors.toList());
        latencyFlux.flatMap(a->a.getItems().stream().map(b->b.))
        Flux<List<List<List<Long>>>> mape = latencyFlux.map(a -> a.getItems().stream().map(b -> b.metrics).map(c -> c.getLatencyp90()).collect(Collectors.toList()));
        latencyFlux.flatMap(a->a.getItems().stream().map(b->b.application))
        Flux<Stream<List<List<Long>>>> map1 = latencyFlux.map(a -> a.getItems().stream().map(b -> b.metrics).map(c -> c.getLatencyp90()));
        Flux<List<Flux<Flux<Long>>>> map = latencyFlux.map(a -> a.getItems().stream().map(b -> b.metrics).map(c -> c.getLatencyp90()).collect(Collectors.toList()));
        using database queries
        Flux<Latency.Item> byItemsMetricsLatencyp90 = repo.findByItemsMetricsLatencyp90();
        Flux<Latency.Item.metrics> map = byItemsMetricsLatencyp90.map(a -> a.metrics).map(b -> b.getLatencyp90());
        return map;
    }*/
}