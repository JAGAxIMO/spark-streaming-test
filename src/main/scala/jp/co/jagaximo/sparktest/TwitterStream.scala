package jp.co.jagaximo.sparktest

import jp.co.jagaximo.sparktest.helper.SparkHelper
import jp.co.jagaximo.sparktest.model.secret.SecretImpl
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.atilika.kuromoji.Tokenizer
import twitter4j.Status

import scala.collection.JavaConversions._


object TwitterStream extends Serializable with SparkHelper {
  def main(args: Array[String]) {
    // stream context作成
    val ssc: StreamingContext = createStreamContext

    // 単語からネガ・ポジ判定出来る辞書を作る
    // 東京工業大学の高村大地先生の単語感情極性対応表を利用させて頂いている
    // http://www.lr.pi.titech.ac.jp/~takamura/index_j.html
    val passionDictionary = SecretImpl.getDictionary.map(_.split(":")).map(
      p => Map(p(0) -> p(3).toFloat, p(1) -> p(3).toFloat)
    ).reduceLeft(_ ++ _)

    // statuses/filterのトラックに該当するリクエストパラメータ
    val filter = List("dwango", "ドワンゴ", "ニコニコ", "niconico")

    // DStream生成
    val twitterStream: DStream[Status] = TwitterUtils.createStream(ssc, Some(SecretImpl.getAuth.get), filter)

    // 形態素に分割し、ネガポジ判定値のRDDを作る
    val toScore = twitterStream.flatMap{ st =>
      val tokenizer = Tokenizer.builder.build
      tokenizer.tokenize(st.getText).toList.map(
        tok => passionDictionary.get(tok.getSurfaceForm)
      )
    }.filter(_.isDefined).map(_.get)


    // 全部合算した値を毎回、スコアが更新される毎に出す
    // 書き込みは30秒毎になっている
    toScore.foreachRDD{rdd =>
      val reduced = rdd.collect().reduce(_ + _)
      println(reduced)
    }

    ssc.start()

    ssc.awaitTermination(1000 * 180)
  }


}

