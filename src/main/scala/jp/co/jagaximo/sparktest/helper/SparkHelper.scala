package jp.co.jagaximo.sparktest.helper

import org.apache.spark.streaming.api.java.JavaStreamingContext
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

trait SparkHelper {
  protected val createSparkConf = new SparkConf().setAppName("stream-test")

  protected val createSparkContext = new SparkContext(createSparkConf)

  protected val createStreamContext: StreamingContext = new StreamingContext(createSparkContext, Seconds(30))
}
