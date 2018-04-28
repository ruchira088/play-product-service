import Dependencies._

name := """play-product-service"""
organization := "com.ruchij"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, BuildInfoPlugin)

buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.eed3si9n.ruchij"

scalaVersion := "2.12.5"

libraryDependencies ++= Seq(
  guice,
  playSlick, playSlickEvolutions, postgresql,
  phantomDsl,
  scalaz,

  scalaTestPlusPlay % Test,
  pegdown % Test,
  h2Database % Test
)

javaOptions in Test += "-Dconfig.file=conf/application.test.conf"

coverageEnabled := true

testOptions in Test +=
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-results")

addCommandAlias("testWithCoverage", "; clean; test; coverageReport")
