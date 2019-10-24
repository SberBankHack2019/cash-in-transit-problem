import sbt.Keys._
import sbt._

lazy val scalaVersion_2_13 = "2.13.0-RC3"
lazy val scalaVersion_2_12 = "2.12.8"
lazy val scalaVersion_2_11 = "2.11.12"

lazy val scalaGraphVersion = "1.13.0"
lazy val scalaGraphDotVersion = "1.12.1"
lazy val pureConfigVersion = "0.11.1"
lazy val javaWebsocketVersion = "1.4.0"
lazy val scalaLoggingVersion = "3.9.2"
lazy val log4jVersion = "2.12.1"
lazy val slf4jVersion = "1.7.5"

val circeVersion = "0.11.1"

lazy val all = project
  .in(file("."))
  .settings(
    defaultSettings ++ Seq(
      organization := "com.sberhack.fun",
      name := "cash-in-transit-problem",
      homepage := Some(url("https://github.com/SberBankHack2019/cash-in-transit-problem")),
      version := "1.0.0",
      libraryDependencies ++= Seq(

      )
    )
  ).aggregate(app, graph, visualisation, algorithm, world)

lazy val app = project
  .in(file("app"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "org.java-websocket" % "Java-WebSocket" % javaWebsocketVersion,
        "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
        "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
        "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
      )
    )
  ).dependsOn(graph, struct)

lazy val graph = project
  .in(file("graph"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "org.scala-graph" %% "graph-core" % scalaGraphVersion,
        "org.scala-graph" %% "graph-constrained" % scalaGraphVersion,
        "org.scala-graph" %% "graph-dot" % scalaGraphDotVersion,
        "com.github.pureconfig" %% "pureconfig" % pureConfigVersion
      )
    )
  ).dependsOn(struct)

lazy val visualisation = project
  .in(file("visualisation"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(

      )
    )
  ).dependsOn(graph)

lazy val algorithm = project
  .in(file("algorithm"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(

      )
    )
  ).dependsOn(graph, visualisation, world)

lazy val world = project
  .in(file("world"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(

      )
    )
  ).dependsOn(graph, visualisation, car, struct, algorithm)

lazy val car = project
  .in(file("car"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(

      )
    )
  ).dependsOn()

lazy val struct = project
  .in(file("struct"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "io.circe" %% "circe-core" % circeVersion,
        "io.circe" %% "circe-generic" % circeVersion,
        "io.circe" %% "circe-parser" % circeVersion
      )
    )
  )

lazy val server = project
  .in(file("server"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "org.java-websocket" % "Java-WebSocket" % javaWebsocketVersion,
        "org.apache.logging.log4j" % "log4j-core" % log4jVersion,
        "org.slf4j" % "slf4j-log4j12" % slf4jVersion,
        "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
      )
    )
  ).dependsOn(struct)

resolvers ++= Seq(
  Resolver.sbtPluginRepo("releases"),
  Resolver.sbtPluginRepo("snapshots"),
  Resolver.sonatypeRepo("public"),
  Resolver.bintrayIvyRepo("com.eed3si9n", "sbt-plugins"),
  DefaultMavenRepository
)

lazy val defaultSettings = Seq(
  scalaVersion := scalaVersion_2_12,

  scalacOptions ++= Seq(
    "-Ypartial-unification",
    "-language:higherKinds",
    "-language:existentials",
    "-Yno-adapted-args",
    "-Yrepl-class-based",
    "-Ywarn-unused:imports",
    "-Ywarn-unused:privates",
    "-Yrangepos"
  ),

  libraryDependencies ++= Seq(
    "dev.zio" %% "zio" % "1.0.0-RC11-1",
    "org.scalatest" %% "scalatest" % "3.0.8" % "test"
  )

)
