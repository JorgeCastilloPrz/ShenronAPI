import play.sbt.PlayImport._

name := "ShenronAPI"

version := "1.0"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.18",
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test" excludeAll(
    ExclusionRule(organization = "com.fasterxml.jackson.datatype"),
    ExclusionRule(organization = "org.scala-lang"),
    ExclusionRule(organization = "org.scala-lang.modules"),
    ExclusionRule(organization = "org.joda"),
    ExclusionRule(organization = "com.typesafe.play"),
    ExclusionRule(organization = "joda-time"),
    ExclusionRule(organization = "org.slf4j"),
    ExclusionRule(organization = "org.scala-lang.modules"),
    ExclusionRule(organization = "junit"),
    ExclusionRule(organization = "com.google.guava")))
    