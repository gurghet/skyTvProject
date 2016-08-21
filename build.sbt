name := "play-slick-rest"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
  evolutions,
  "com.h2database" % "h2" % "1.4.192",
  cache,
  ws,
  "com.lihaoyi" %% "scalatags" % "0.6.0",
  "org.scalatestplus.play" % "scalatestplus-play_2.11" % "1.5.1",
  specs2 % Test
)
scalacOptions in Test ++= Seq("-Yrangepos")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

