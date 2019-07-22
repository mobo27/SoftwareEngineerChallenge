#Design A Google Analytic like Backend System

##Introduction
As we have to handle high volume of write data with very reliable structure, we will consider using more asynchronous structure for handling the analysis task.
Re-processing is necessary to avoid any bug in the processing logic.

##Requirement Analysis

###Average thoughput

###Data
Handle large write volume: 1-3 billions per day:
e.g. 10KB/access*1-3billions => 10-30TB
time-series related metrics

###Structure
Ability to reprocess historical data in case of bugs in the processing logic

###SLA
Time Delay: within 1 hour
Minimum downtime

##Possible Tools and Structute
Data Collection/Data Input/Message Queue
Kafka, Redis DB, Rabbit MQ, Active MQ, 
Selection Kafka:
Kafka have high availability and horizontal scalable, if some cluster need to upgrade, the service can be partially shutdown for upgrade without any downtime

Data Processing
Apache Spark, Apache Storm, Apache Flink, Apache Beam(includes flint and spark)
Selection: Apache Spark since we are processing metrics to customer, so micro-batch processing is fit for this scene.
If we are going to build up a fraud-detection system, Apache Link may be a better choice since it have much more support on real-time processing of some the input data and calculate the index value.

Raw Data Storage
InfluexDB, Apache Hadoop, Cassandra, MongoDB
Cassandra: Since we have to provide the few

Processed Data Storage
InfluexDB, Apache Hadoop, Cassandra, MongoDB

InfluexDB


