package daos

import scala.util.{Success, Try}

trait DaoType
{
  val envValue: String = getClass.getSimpleName.init.toUpperCase
}

object DaoType
{
  case object Slick extends DaoType

  case object Cassandra extends DaoType

  def unapply(envValue: Try[String]): Option[DaoType] =
    envValue match {
      case Success(Slick.envValue) => Some(Slick)
      case Success(Cassandra.envValue) => Some(Cassandra)
      case _ => None
    }
}
