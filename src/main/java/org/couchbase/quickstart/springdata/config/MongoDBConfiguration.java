package org.couchbase.quickstart.springdata.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@Profile("mongodb")
public class MongoDBConfiguration extends AbstractMongoClientConfiguration {

     @Value("${spring.data.mongodb.database}")
     private String dbName;

     @Value("${spring.data.mongodb.uri}")
     private String uri;

     @Override
     protected String getDatabaseName() {
          return dbName;
     }

     @Override
     @Bean
     public MongoClient mongoClient() {
          return MongoClients.create(uri);
     }
}
