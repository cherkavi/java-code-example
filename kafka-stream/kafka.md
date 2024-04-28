## docs
* [java example](https://developer.confluent.io/tutorials/creating-first-apache-kafka-streams-application/kstreams.html)
   https://github.com/confluentinc/kafka-streams-examples/blob/7.6.1-post/src/main/java/io/confluent/examples/streams/SecureKafkaStreamsExample.java
* [how to use single message transformation](https://docs.confluent.io/platform/current/connect/transforms/overview.html)
* Kafka Stream
  * [Start with the DSL](https://github.com/confluentinc/examples)
  * [Documentation](http://docs.confluent.io/current/streams/)
  * [Run the application](http://docs.confluent.io/current/streams/developer-quide.html#running-a-kafka-streams-application)
    https://docs.confluent.io/current/tutorials/examples/microservices-orders/docs/index.html#tutorial-microservi
  KStreams library - github.com/michelin/kstreamplify

## python project 
> https://developer.confluent.io/get-started/python/

### start docker containers
```sh
# pip3 install kafka-python

docker-compose up -d
# docker-compose down
docker ps

export KAFKA_BROKER=localhost:9092
export KAFKA_TOPIC=playground.topic
```

### create topic
```sh
x-www-browser http://localhost:9021/

# docker-compose exec broker kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic playground.topic
# docker-compose exec broker kafka-topics --list --bootstrap-server $KAFKA_BROKER
```

### java stream processor
```sh
temp; cd kafka/stream-processor

KAFKA_TOPIC_STREAM_INPUT=stream-processor-input
KAFKA_TOPIC_STREAM_OUTPUT=stream-processor-output

# mkdir -p src/main/java/io/confluent/developer
# code src/main/java/io/confluent/developer/Util.java
# code src/main/java/io/confluent/developer/KafkaStreamsApplication.java

# gradle -Dhttp.proxyHost=localhost -Dhttp.proxyPort=48157 -Dhttps.proxyHost=localhost -Dhttps.proxyPort=48157 build
# gradle --refresh-dependencies build
gradle shadowJar
java -jar build/libs/creating-first-apache-kafka-streams-application-*.jar configuration/dev.properties
```

## trainings education

* kafka streams
  
  * [official kafka streams](https://kafka.apache.org/documentation/streams/)
    * [write application](https://kafka.apache.org/37/documentation/streams/tutorial)
    * [quarkus kafka stream](https://quarkus.io/guides/kafka-streams)
  