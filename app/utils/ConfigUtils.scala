package utils

import exceptions.EnvValueUndefinedException
import constants.DefaultConfigs
import ScalaUtils.toTry

import scala.util.Try
import scala.language.implicitConversions

object ConfigUtils
{
  case class EnvironmentVariables(envMap: Map[String, String])

  object EnvironmentVariables
  {
    implicit def toMap(environmentVariables: EnvironmentVariables): Map[String, String] =
      environmentVariables.envMap
  }

  def getEnvValue(name: String)(implicit environmentVariables: EnvironmentVariables): Try[String] =
    toTry(environmentVariables.get(name), EnvValueUndefinedException(name))

  def getEnvValueList(name: String)(implicit environmentVariables: EnvironmentVariables): Try[List[String]] =
    for {
      envValue <- getEnvValue(name)
    }
    yield envValue.split(DefaultConfigs.ENV_VALUE_DELIMITER).toList

  def terminate(throwable: Throwable): Unit =
  {
    System.err.println(throwable.getMessage)
    throwable.printStackTrace()
    sys.exit(1)
  }
}