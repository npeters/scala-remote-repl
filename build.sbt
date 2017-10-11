import sbt.Keys._

name := "remote-repl"

organization := "net.nworks"

val agent = project.in(file("agent")).settings(
  packageOptions in (Compile, packageBin) +=
    Package.ManifestAttributes(
      "Agent-Class" -> "com.nworks.remote.agent.AgentMain",
      "Premain-Class"-> "com.nworks.remote.agent.AgentMain"
      ),
  scalaVersion := "2.11.8"
)

val main = project.in(file(".")).aggregate(agent).settings(
  unmanagedJars in Compile ~= {uj =>
    Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3)+"lib/tools.jar"))) ++ uj
  },
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scala-lang.modules" % "scala-jline" % "2.12.1",
    "com.typesafe" % "config" % "1.2.0"
  ),
  scalaVersion := "2.11.8"
)


