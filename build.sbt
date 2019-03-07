name := """javabytes"""

version := "4.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "Maven" at "https://mvnrepository.com/artifact/"

scalaVersion := "2.11.12"

libraryDependencies += guice
libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3-1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings"
)
