package com.axon.cfs.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories("com.axon.cfs.repository")
@EnableAutoConfiguration
public class MongoDbConfig extends AbstractMongoClientConfiguration{
	
	@Value("${spring.data.mongodb.uri}")
	private String connectionString;
	
	@Value("${spring.data.mongodb.db.name}")
	private String dbName;
	

	@Bean
	@Override
	public MongoClient mongoClient() {
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		return MongoClients.create(MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(connectionString+dbName))
				.codecRegistry(codecRegistry)
				.build());
	}

	@Override
	protected String getDatabaseName() {
		return dbName;
	}
	
}
