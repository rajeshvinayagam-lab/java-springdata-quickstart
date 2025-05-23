package org.couchbase.quickstart.springdata.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseCustomConversions;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.core.mapping.CouchbaseMappingContext;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

import com.couchbase.client.core.error.BucketNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.env.ClusterEnvironment;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCouchbaseRepositories(basePackages = "org.couchbase.quickstart.springdata.repository.couchbase")
public class CouchbaseConfiguration {

  @Value("#{systemEnvironment['DB_CONN_STR'] ?: '${spring.couchbase.bootstrap-hosts:localhost}'}")
  private String host;

  @Value("#{systemEnvironment['DB_USERNAME'] ?: '${spring.couchbase.bucket.user:Administrator}'}")
  private String username;

  @Value("#{systemEnvironment['DB_PASSWORD'] ?: '${spring.couchbase.bucket.password:password}'}")
  private String password;

  @Value("${spring.couchbase.bucket.name:travel-sample}")
  private String bucketName;

  @Value("${spring.couchbase.scope.name:inventory}")
  private String scopeName;

  @Bean(destroyMethod = "disconnect")
  public Cluster couchbaseCluster(ClusterEnvironment couchbaseClusterEnvironment) {
    try {
      log.debug("Connecting to Couchbase cluster at " + host);
      return Cluster.connect(host, username, password);
    } catch (Exception e) {
      log.error("Error connecting to Couchbase cluster", e);
      throw e;
    }
  }

  @Bean
  public Bucket getCouchbaseBucket(Cluster cluster) {
    try {
      if (!cluster.buckets().getAllBuckets().containsKey(bucketName)) {
        log.error("Bucket with name {} does not exist. Creating it now", bucketName);
        throw new BucketNotFoundException(bucketName);
      }
      return cluster.bucket(bucketName);
    } catch (Exception e) {
      log.error("Error getting bucket", e);
      throw e;
    }
  }

  @Bean
  public ClusterEnvironment clusterEnvironment() {
    return ClusterEnvironment.builder().build();
  }

  @Bean
  public CouchbaseClientFactory couchbaseClientFactory(Cluster cluster) {
    return new SimpleCouchbaseClientFactory(cluster, bucketName, scopeName);
  }

  @Bean
  public CouchbaseCustomConversions couchbaseCustomConversions() {
    return new CouchbaseCustomConversions(List.of());
  }

  @Bean
  public CouchbaseMappingContext couchbaseMappingContext(CouchbaseCustomConversions conversions) {
    CouchbaseMappingContext context = new CouchbaseMappingContext();
    context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
    return context;
  }

  @Bean
  public MappingCouchbaseConverter mappingCouchbaseConverter(
          CouchbaseMappingContext mappingContext,
          CouchbaseCustomConversions conversions
  ) {
    MappingCouchbaseConverter converter = new MappingCouchbaseConverter(mappingContext);
    converter.setCustomConversions(conversions);
    return converter;
  }

  @Bean(name = "couchbaseTemplate")
  public CouchbaseTemplate couchbaseTemplate(
          CouchbaseClientFactory factory,
          MappingCouchbaseConverter converter
  ) {
    return new CouchbaseTemplate(factory, converter);
  }

  @Bean
  public RepositoryOperationsMapping couchbaseRepositoryOperationsMapping(
          @Qualifier("couchbaseTemplate") CouchbaseTemplate template
  ) {
    return new RepositoryOperationsMapping(template);
  }


}
