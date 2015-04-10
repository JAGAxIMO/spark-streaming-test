import sbtassembly.MergeStrategy

name := "spark-test"

organization := "jp.co.jagaximo"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.4"

javaOptions ++= Seq("-encoding", "UTF-8")

resolvers ++= Seq(
  "Typesafe Releases" at "https://repo.typesafe.com/typesafe/releases/",
  "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika"
)

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.2.0" % "provided",
  "org.apache.spark" % "spark-streaming_2.10" % "1.2.0" % "provided",
  "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.2.0",
  "org.atilika.kuromoji" % "kuromoji" % "0.7.7"
)

mainClass in assembly := Some("CommentCount")

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "mailcap" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "Logger.class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "Log.class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "class" => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "mimetypes.default" => MergeStrategy.concat
  case PathList(ps @ _*) if ps.last endsWith "plugin.properties" => MergeStrategy.filterDistinctLines
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
  case _ => MergeStrategy.first
}
