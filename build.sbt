name := "rabbitmq-akka-actors"

version := "1.0"

organization := "3pillarglobal.com"

scalaVersion := "2.11.6"

resolvers ++= Seq(
  "snapshots"           at "http://oss.sonatype.org/content/repositories/snapshots",
  "releases"            at "http://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka"          %% "akka-actor"                      % "2.3.11",
    "com.typesafe.akka"           % "akka-stream-experimental_2.11"   % "1.0-RC4",
    "com.github.sstone"           % "amqp-client_2.11"                % "1.5",
    "org.easyrules"               % "easyrules-core"                  % "2.0.0"
  )
}


fork in run := true