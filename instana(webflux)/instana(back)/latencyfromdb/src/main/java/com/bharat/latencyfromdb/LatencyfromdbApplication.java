package com.bharat.latencyfromdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@SpringBootApplication
public class LatencyfromdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(LatencyfromdbApplication.class, args);
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
}
