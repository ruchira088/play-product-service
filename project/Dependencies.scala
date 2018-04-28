import sbt._

object Dependencies
{
  val PLAY_SLICK_VERSION = "3.0.3"
  lazy val playSlick = "com.typesafe.play" %% "play-slick" % PLAY_SLICK_VERSION
  lazy val playSlickEvolutions = "com.typesafe.play" %% "play-slick-evolutions" % PLAY_SLICK_VERSION
  lazy val postgresql = "org.postgresql" % "postgresql" % "42.2.2"
  lazy val h2Database = "com.h2database" % "h2" % "1.4.197"

  lazy val phantomDsl = "com.outworkers" %% "phantom-dsl" % "2.24.2"

  lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.21"

  lazy val scalaTestPlusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"

  lazy val pegdown = "org.pegdown" % "pegdown" % "1.6.0"
}
