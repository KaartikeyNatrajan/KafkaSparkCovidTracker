# Covid 19 Tracker
This project is a data aggregation platform using Java, Apache Kafka and Spark to process streams of COVID-19 data from
multiple sources into a unified tracking dashboard built using React.

## Setup and Usage

### 1. Zookeeper
Apache Zookeeper is the centralized service in this project and is used to maintain naming and configuration data as well as provide flexible and robust synchronization within the distributed systems. Zookeeper keeps track of status of the Kafka cluster nodes and it also keeps  track of Kafka topics, partitions etc.

#### Execution 
```
cd /kafka_home
bin/zookeeper-server-start.sh config/zookeeper.properties
```

### 2. Apache Kafka Server
Apache Kafka is the open-source distributed event streaming platform we use to build and manage high-performance data pipelines, perform streaming analytics and data integration. The Kafka Producer and Consumers are run on the default ports.

#### Execution 
```
cd /kafka_home
bin/kafka-server-start.sh config/server.properties
```

### 3. Mock data streaming from multiple sources
The data is currently being mocked from static JSON datasets sourced from open data sources. We create a simple node backend that mocks the streaming of this data into the Kafka pipeline from the multiple sources simultaneously.

#### Execution 
```
cd /DataProducer
node index.js
```

### 4. Run Spring Application to view Dashboard
The dashboard is created using React and is hosted locally built as a Spring Boot application. We run basic queries using Spark SQL to perform data analysis and aggregate the sourced information into graphs on the dashboard.


#### Execution 
```
cd /CovidTracker
./mvnw spring-boot:run
```
open [http://localhost:9000/dashboard](https://localhost:9000/dashboard)


