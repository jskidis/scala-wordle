ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val scalaTestVersion = "3.2.10"
val akkaVersion = "2.6.18"
val akkaHttpVersion = "10.2.7"

lazy val root = (project in file("."))
  .settings(
    name := "scala-wordle",
    idePackagePrefix := Some("com.skidis.wordle"),
    Global / excludeLintKeys += idePackagePrefix,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "3.2.10",
      "org.scalatest" %% "scalatest" % "3.2.10" % Test,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    )
  )
