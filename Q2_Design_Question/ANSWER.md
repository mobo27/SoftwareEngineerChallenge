# Design A Google Analytic like Backend System

## Introduction
As we have to handle high volume of write data with very reliable structure, we will consider using more asynchronous structure for handling the analysis task.
Re-processing is necessary to avoid any bug in the processing logic.

I will only go though high level design in this answers and detail explaim why I will chose this structure.


## Requirement Analysis

### Average thoughput
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

## Possible Tools and Structute
- Data Collection/Data Input/Message Queue
*  Apache Kafka
*  Redis DB
*  Rabbit MQ
*  Active MQ
*Selection: Apache Kafka*
Kafka have high availability and horizontal scalable, if some cluster need to upgrade, the service can be partially shutdown for upgrade without any downtime

- Data Processing
*  Apache Spark
*  Apache Storm
*  Apache Flink
*  Apache Beam(combination of flint and spark)
*Selection: Apache Spark*
Since we are processing metrics to customer, so micro-batch processing is fit for this scene.
If we are going to build up a fraud-detection system, Apache Link may be a better choice since it have much more support on real-time processing of some the input data and calculate the index value.

- Raw Data Storage
InfluexDB, Apache Hadoop, Cassandra, MongoDB
*Selection: Cassandra*
Since we have to provide the few

- Processed Data Storage
InfluexDB, Apache Hadoop, Cassandra, MongoDB
*Selection: InfluexDB*

## Overall Structure
### Data Input Flow and data processing
\[DataProducer/AppServer\]->\[Apache Kafka\]->\[Cassandra(for Raw data)\]
		                        \[Apache Kafka\]->\[Apache Spark\]->\[InfluexDB(for Processed data)\]
>We need Apache Kafka to handle huge amount of write request as a queue, then writing them to DB in bluk
>since bluk write can improve the speed of write speed and reduce the load to the database to handle larger load.
>Also Apache Kafka have a feature to group different data to send them to different data consumer. 

### Data Reproduce Flow
\[Cassandra(for Raw data)\]->\[Apache Spark\]->\[InfluexDB(for Processed data)\]

### Data Consume flow
\[InfluexDB(for Processed data)\]->\[AppServer\]->\[HTTP/Websocket\]->\[Load Balancer\]->\[User/Merchant\]

