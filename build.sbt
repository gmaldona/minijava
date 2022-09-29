ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

enablePlugins(Antlr4Plugin)
antlr4Version in Antlr4 := "4.10.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"

lazy val root = (project in file("."))
  .settings(
    name := "minijava"
  )


