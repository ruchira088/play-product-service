import Dependencies._

name := """play-product-service"""
organization := "com.ruchij"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, BuildInfoPlugin)

buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.eed3si9n.ruchij"

scalaVersion := "2.12.5"

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

libraryDependencies ++= Seq(
  guice,
  playSlick, playSlickEvolutions, postgresql,
  scalaz,

  scalaTestPlusPlay % Test,
  h2Database % Test
)
