package jp.co.jagaximo.sparktest.model


trait Auth {
  val consumerKey: String
  val consumerSecret: String
  val accessToken: String
  val accessTokenSecret: String
}
