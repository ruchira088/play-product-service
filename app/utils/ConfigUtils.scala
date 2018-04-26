package utils

import exceptions.EnvValueUndefinedException

import scala.util.Try

object ConfigUtils
{
  type EnvironmentVariables = Map[String, String]

  def getEnvValue(name: String)(implicit environmentVariables: EnvironmentVariables): Try[String] =
    ScalaUtils.toTry(environmentVariables.get(name), EnvValueUndefinedException(name))
}