name := """play-product-service"""
organization := "com.ruchij"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, BuildInfoPlugin)

buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion)
buildInfoPackage := "com.eed3si9n.ruchij"

scalaVersion := "2.12.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
