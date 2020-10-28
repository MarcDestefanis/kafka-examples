## Start Kafka and Zookeeper

Start the Kafka and Zookeeper containers
```shell script
docker-compose up -d
```

## SSH into Kafka container

SSH into the Kafka container
```shell script
docker exec -it kafka /bin/sh
```

## Kafka CLI examples
#### Simple producer and consumer

Create a `orders` topic
```shell script
$KAFKA_HOME/bin/kafka-topics.sh --create --topic orders --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
Write messages to the topic
```shell script
$KAFKA_HOME/bin/kafka-console-producer.sh --topic=orders --broker-list localhost:9092
```
Consume messages sent by the producer from beginning
```shell script
$KAFKA_HOME/bin/kafka-console-consumer.sh --topic=orders --from-beginning --bootstrap-server localhost:9092
```

#### Producer and consumer within a consumer group

Create a `locations` topic
```shell script
$KAFKA_HOME/bin/kafka-topics.sh --create --topic locations --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```
Write messages to the topic
```shell script
$KAFKA_HOME/bin/kafka-console-producer.sh --topic locations --broker-list localhost:9092
```
Consume messages within a consumer group
```shell script
$KAFKA_HOME/bin/kafka-console-consumer.sh --topic locations --group group-ABC --bootstrap-server localhost:9092
```

#### Retrieve and describe topics

Retrieve the list of topics
```shell script
$KAFKA_HOME/bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```
Describe the `orders` topic
```shell script
$KAFKA_HOME/bin/kafka-topics.sh --describe --topic orders --bootstrap-server localhost:9092
```

#### Retrieve and describe consumer groups

Retrieve the list of consumer groups
```shell script
$KAFKA_HOME/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```
```shell script
$KAFKA_HOME/bin/kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --all-groups
```

#### Manage offset

Reset offsets
```shell script
$KAFKA_HOME/bin/kafka-consumer-groups.sh --reset-offsets --to-offset 0 --bootstrap-server localhost:9092 --execute --group group-ABC --topic locations
```
Reset offset of a specific topic:partition
```shell script
$KAFKA_HOME/bin/kafka-consumer-groups.sh --reset-offsets --to-offset 1 --bootstrap-server localhost:9092 --execute --group group-ABC --topic locations:2
```
Shift offset by 'n', where 'n' can be positive or negative
```shell script
$KAFKA_HOME/bin/kafka-consumer-groups.sh --reset-offsets --shift-by -2 --bootstrap-server localhost:9092 --execute --group group-ABC --topic locations
```

#### Find Kafka cluster version
```shell script
$KAFKA_HOME/bin/kafka-consumer-groups.sh --version
```

## Stop Kafka and Zookeeper

Stop the containers
```shell script
docker-compose down
```

---

## Setup Kafka Tool UI

#### Download
https://www.kafkatool.com/download.html

#### Configure connection to Kafka cluster
Configure the Properties tab with
- Cluster name
- Kafka cluster version ([see here](#Find-Kafka-cluster-version))
- Zookeeper Host (localhost)
- Zookeeper Port (2181)