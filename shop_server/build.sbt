name := "shop_server"

version := "1.0"

lazy val `shop_server` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
resolvers += "Atlassian's Maven Public Repository" at "https://packages.atlassian.com/maven-public/"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq( ehcache, ws, specs2 % Test, guice,
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.play" %% "play-slick" % "5.0.2",
  "org.xerial" % "sqlite-jdbc" % "3.36.0.3",
  "net.liftweb" %% "lift-json" % "3.5.0",
  "com.iheart" %% "ficus" % "1.5.2",
  "com.mohiva" %% "play-silhouette" % "7.0.0",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "7.0.0",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "7.0.0",
  "com.mohiva" %% "play-silhouette-persistence" % "7.0.0",
  "net.codingwell" %% "scala-guice" % "5.0.1")