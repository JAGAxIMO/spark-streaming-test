Spark Streaming Test
===================

spark-streaming hands on query repository

# Usage

At first, you must Download [spark source](https://github.com/apache/spark), building , and start master node


and create src/scala/jp/co/jagaximo/sparktest/model/secret/SecretImpl.scala extends Secret

```
sbt assembly

/path/to/spark/bin/spark-submit
--class jp.co.jagaximo.sparktest.TwitterStream
--master spark://example.com
--num-executors 2
--driver-memory 5g
--executor-memory 10g
./target/scala_2.10/spark-stream-test-assembly-0.1-SNAPSHOT.jar > result.txt

```


