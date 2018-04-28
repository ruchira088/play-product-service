package daos

import scala.util.{Success, Try}

trait DaoType
{
  val envValue: String
}

object DaoType
{
  case object Slick extends DaoType
  {
    override val envValue: String = "SLICK"
  }

  case object Cassandra extends DaoType
  {
    override val envValue: String = "CASSANDRA"
  }

  def unapply(envValue: Try[String]): Option[DaoType] =
    envValue match {
      case Success(Slick.envValue) => Some(Slick)
      case Success(Cassandra.envValue) => Some(Cassandra)
      case _ => None
    }
}
