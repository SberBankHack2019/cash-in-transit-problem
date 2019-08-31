import sbt.Keys._
import sbt._

lazy val scalaVersion_2_13 = "2.13.0-RC3"
lazy val scalaVersion_2_12 = "2.12.8"
lazy val scalaVersion_2_11 = "2.11.12"

lazy val all = project
  .in(file("."))
  .settings(
    defaultSettings ++ Seq(
      organization := "sberhack.fun",
      name := "cash-in-transit-problem",
      homepage := Some(url("https://github.com/SberBankHack2019/cash-in-transit-problem")),
      version := "1.0.0",
      libraryDependencies ++= Seq(
        "dev.zio" %% "zio" % "1.0.0-RC11-1",
        "org.scalatest" %% "scalatest" % "3.0.8" % "test"
      )
    )
  ).aggregate(app)

lazy val app = project
  .in(file("app"))
  .settings(
    defaultSettings ++ Seq(
      libraryDependencies ++= Seq(
        "dev.zio" %% "zio" % "1.0.0-RC11-1",
        "org.scalatest" %% "scalatest" % "3.0.8" % "test"
      )
    )
  )

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
  )

)
