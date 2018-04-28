package modules

import com.google.inject.AbstractModule
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import constants.{DefaultConfigs, EnvVariableNames}
import daos.DaoType._
import daos.phantom.PhantomTagDao
import daos.slick.SlickTagDao
import daos.{DaoType, TagDao}
import utils.ConfigUtils._

import scala.util.Try

class GuiceBindingsModule extends AbstractModule
{
  import GuiceBindingsModule._

  override def configure(): Unit =
  {
    implicit val environmentVariables: EnvironmentVariables = EnvironmentVariables(sys.env)

    bind(classOf[EnvironmentVariables]).toInstance(environmentVariables)
    bindDaoType()
  }

  private def bindDaoType()(implicit environmentVariables: EnvironmentVariables) =
    getEnvValue(EnvVariableNames.DAO_TYPE) match {
      case DaoType(Slick) => bindSlickDao()
      case DaoType(Cassandra) => bindCassandraDao()
      case _ => bindSlickDao()
    }

  private def bindSlickDao() =
  {
    bind(classOf[TagDao]).to(classOf[SlickTagDao])
  }

  private def bindCassandraDao()(implicit environmentVariables: EnvironmentVariables) =
  {
    bind(classOf[CassandraConnection]).toInstance(cassandraConnection().get)
    bind(classOf[TagDao]).to(classOf[PhantomTagDao])
  }
}

object GuiceBindingsModule
{
  def cassandraConnection()(implicit environmentVariables: EnvironmentVariables): Try[CassandraConnection] =
    for {
      cassandraHosts <- getEnvValueList(EnvVariableNames.CASSANDRA_HOSTS)
    }
    yield ContactPoints(cassandraHosts).keySpace(DefaultConfigs.CASSANDRA_KEYSPACE)
}
