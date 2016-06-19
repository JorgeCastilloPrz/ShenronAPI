import play.sbt.PlayImport._

name := "ShenronAPI"

version := "1.0"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test
)

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % "test"
    