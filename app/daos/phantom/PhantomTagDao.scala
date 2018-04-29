package daos.phantom

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import daos.TagDao
import daos.phantom.tables.PhantomProductTagTable
import javax.inject.{Inject, Singleton}
import models.ProductTag
import play.api.inject.ApplicationLifecycle
import scalaz.OptionT
import utils.ConfigUtils.terminate

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class PhantomTagDao @Inject()(connector: CassandraConnection, applicationLifecycle: ApplicationLifecycle)
                             (implicit executionContext: ExecutionContext)
  extends Database[PhantomTagDao](connector) with TagDao
{
  object productTags extends PhantomProductTagTable with Connector

  override def insert(productTag: ProductTag)(implicit executionContext: ExecutionContext): Future[ProductTag] =
    for {
      resultSet <- productTags.store(productTag).future()
    }
    yield productTag

  override def findByName(name: String)(implicit executionContext: ExecutionContext): OptionT[Future, ProductTag] =
    OptionT {
      for {
        results <- productTags.select.where(_.name is name).fetch()
      }
      yield results.headOption
    }

  def init(): Future[Seq[ResultSet]] = productTags.create.ifNotExists().future()

  init().onComplete {

    case Success(_) =>
      println(classOf[PhantomTagDao].getSimpleName + " has been successfully initialized.")

    case Failure(throwable) => terminate(throwable)
  }

  applicationLifecycle.addStopHook {
    () => Future.successful(shutdown())
  }
}