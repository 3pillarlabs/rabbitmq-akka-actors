name := "rabbitmq-akka-actors"

version := "1.0"

organization := "threepillarglobal.atg"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "snapshots"           at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"            at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka"          %%  "akka-actor"               % "2.3.11"
  )
}


fork in run := true