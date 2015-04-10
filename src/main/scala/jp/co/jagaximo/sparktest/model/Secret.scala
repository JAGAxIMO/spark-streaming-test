package jp.co.jagaximo.sparktest.model

import twitter4j.auth.Authorization

trait Secret {
  val getAuth: Option[Authorization]
  def getDictionary: Seq[String]
}

