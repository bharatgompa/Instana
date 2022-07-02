package com.bharat.instana;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Component;
import java.time.Instant;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class InstanaApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(InstanaApplication.class, args);
	}
	@Bean
	public MappingMongoConverter mongoConverter(MongoDatabaseFactory mongoFactory, MongoMappingContext mongoMappingContext)
	{
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
		MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
		mongoConverter.setMapKeyDotReplacement("");
		return mongoConverter;
	}
	@Bean
	public ObjectMapper objectMapper()
	{
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}
	@Component
	public class RunAfterStartup {

		@EventListener(ApplicationReadyEvent.class)
		public void runAfterStartup() throws InterruptedException, IOException {
			long num = 1655353800000L;  //june 16 10am
			while(true) {
				//Thread.sleep(4000);
				URL url = new URL("http://localhost:8081/latency");
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				http.setRequestProperty("authorization", "apiToken FqbarBTdT7-24euiDFSb-A");
				http.setRequestProperty("content-type", "application/json");
				long windowSize = 600000L;
				num += windowSize;
				System.out.println(num);
			    String data = "{\n  \"metrics\":[\n      {\n          \"metric\":\"latency\",\n          \"aggregation\":\"P90\"\n      }\n]\n,      \"timeframe\": {\n    \"windowSize\": 600000,\n    \"to\":"+num+" \n}\n  \n  }";
				byte[] out = data.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = http.getOutputStream();
				stream.write(out);
				System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
				if(num==1656261000000L) //june 26 10pm
				{
					break;
				}
				http.disconnect();
			}
		}
	}
}
