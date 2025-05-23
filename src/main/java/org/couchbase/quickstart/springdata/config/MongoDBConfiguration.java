package org.couchbase.quickstart.springdata.config;

import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "org.couchbase.quickstart.springdata.repository.mongodb"
)
public class MongoDBConfiguration {

     @Value("${spring.data.mongodb.database}")
     private String dbName;

     @Value("${spring.data.mongodb.uri}")
     private String uri;

     @Bean
     public MongoClient mongoClient() {
          return MongoClients.create(uri);
     }

     @Bean
     public MongoTemplate mongoTemplate() {
          return new MongoTemplate(mongoClient(), dbName);
     }

     @Bean(name = "mongoCustomConversions")
     public MongoCustomConversions mongoCustomConversions() {
          return new MongoCustomConversions(List.of());
     }
}
