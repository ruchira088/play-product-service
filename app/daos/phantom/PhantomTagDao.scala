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

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PhantomTagDao @Inject()(connector: CassandraConnection, applicationLifecycle: ApplicationLifecycle)
                             (implicit executionContext: ExecutionContext)
  extends Database[PhantomTagDao](connector) with TagDao with PhantomDao
{
  self =>

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

  override def init(): Future[Seq[ResultSet]] = productTags.create.ifNotExists().future()

  override def onShutdown(): Unit = shutdown()

  PhantomDao.initialize(self, applicationLifecycle)
}