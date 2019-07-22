
# Design A Google Analytic like Backend System

## Introduction
As we have to handle high volume of write data with very reliable structure, we will consider using more asynchronous structure for handling the analysis task.

Re-processing is necessary to avoid any bug in the processing logic.

I will only go though high level design in this answers and detail explain for the whole structure.
## Requirement Analysis
### Average throughput
1billions/(24*60*60) = 11k request per seconds of write request

### Data Structure
Handle large write volume: 1-3 billions per day:
e.g. 10KB/access*billions => 10-99TB
time-series related metrics

### Requirement
Ability to reprocess historical data in case of bugs in the processing logic

### SLA
- Time Delay: within 1 hour
- Minimum downtime

## Possible Tools and Structure
 ### Data Collection/Data Input/Message Queue
 1. Apache Kafka
 2. Redis DB
 3. Rabbit MQ
 4. Active MQ

***Selection: Apache Kafka***

- Message Queue Feature
- Storage of raw data
- High Availability and scalable

Kafka is scalable for large processing, while zookeeper is playing an very important role to keep all node online.

As we are going to produce up to 10TB of raw data everyday, Kafka compression feature is also important for the system otherwise disk space will be full soon.

### Data Processing
 - Apache Spark
 - Apache Storm
 - Apache Flink
 - Apache Beam(combination of flint and spark)

***Selection: Apache Spark***

 - Micro-batch processing is fit for the usage.
 - Support batch processing and real-time stream processing.

Additional comments:
If we are going to build up a fraud-detection system, Apache Link may be a better choice since it have much more support on real-time processing of some the input data and calculate the index value.
  
### Processed Data Storage
InfluxDB, Apache Hadoop, Cassandra, MongoDB

***Selection: Cassandra***

- High Availability.
- High insert performance.
- NoSQL structure can allow easily implementation of new data structure without major changes.

Additional comments:
Although InfluxDB is very famous for handling time-series data, the open source version of InfluxDB does not support the clustering feature while Casandra support it. 

## Overall Structure
### Data Input Flow and data processing

\[DataProducer/AppServer\]->\[Apache Kafka\]->\[Apache Spark\]->\[Cassandra\]

Apache Kafka is acting two important role in this structure, message queue and raw data storage. Kafka will group necessary as configured and distributed it to data consumer.

### Data Reproduce Flow
\[Apache Kafka\]->\[Apache Spark\]->\[Cassandra\]

Since Apache Kafka have the feature of storing the data, if data processing is necessary, we can retrieve the data from Apache Kafka in the designated timespan.

### Access of the transformed data
\[User/Merchant\]->[HTTP & Websocket\]->\[Load Balancer\]->\[AppServer(s)\]->\[Cassandra\]

To access the data, we will use the load balancer structure, also we should consider to use rolling deployment in AppServers to archive a 0 down time release.

Web-socket can be used as main data retrieval channel or provided as an additional update while new data was processed. 

## Conclusion
To provide a real-time metrics to user with large data volume is challenging, we can offer our customer by semi-realtime results by very small batch processing and appropriate data stream, which can reduce the complexity of the system, reduce of cost and raise of stability.
