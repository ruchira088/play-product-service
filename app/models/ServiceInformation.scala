package models

import scala.util.Properties.javaVersion

case class ServiceInformation(
      serviceName: String,
      scalaVersion: String,
      sbtVersion: String,
      javaVersion: String
)

object ServiceInformation
{
  import com.eed3si9n.ruchij.BuildInfo._

  def apply(): ServiceInformation = ServiceInformation(name, scalaVersion, sbtVersion, javaVersion)
}
