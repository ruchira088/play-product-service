package modules

import com.google.inject.AbstractModule
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import constants.{DefaultConfigs, EnvVariableNames}
import daos.DaoType._
import daos.phantom.{PhantomProductDao, PhantomTagDao}
import daos.slick.SlickTagDao
import daos.{DaoType, ProductDao, TagDao}
import utils.ConfigUtils._

import scala.util.Try
import scala.util.control.NonFatal

class GuiceBindingsModule extends AbstractModule
{
  import GuiceBindingsModule._

  override def configure(): Unit =
    bindings().recover {
      case NonFatal(throwable) => terminate(throwable)
    }

  private def bindings(): Try[Unit] =
  {
    implicit val environmentVariables: EnvironmentVariables = EnvironmentVariables(sys.env)

    bind(classOf[EnvironmentVariables]).toInstance(environmentVariables)

    for {
      _ <- bindDaoType()
    }
    yield (): Unit
  }

  private def bindDaoType()(implicit environmentVariables: EnvironmentVariables): Try[_] =
    getEnvValue(EnvVariableNames.DAO_TYPE) match {
      case DaoType(Slick) => bindSlickDao()
      case DaoType(Cassandra) => bindCassandraDao()
      case _ => bindSlickDao()
    }

  private def bindSlickDao(): Try[_] =
    Try {
      bind(classOf[TagDao]).to(classOf[SlickTagDao])
    }

  private def bindCassandraDao()(implicit environmentVariables: EnvironmentVariables): Try[_] =
    cassandraConnection()
      .map {
        connection => {
          bind(classOf[CassandraConnection]).toInstance(connection)
          bind(classOf[TagDao]).to(classOf[PhantomTagDao]).asEagerSingleton()
          bind(classOf[ProductDao]).to(classOf[PhantomProductDao]).asEagerSingleton()
        }
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
