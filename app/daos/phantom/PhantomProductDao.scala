package daos.phantom

import com.outworkers.phantom.builder.clauses.QueryCondition
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import daos.ProductDao
import daos.phantom.tables.PhantomProductTable
import javax.inject.{Inject, Singleton}
import models.Product
import play.api.inject.ApplicationLifecycle
import scalaz.OptionT
import shapeless.HList

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PhantomProductDao @Inject()(connection: CassandraConnection, applicationLifecycle: ApplicationLifecycle)
                                 (implicit executionContext: ExecutionContext)
  extends Database[PhantomProductDao](connection) with ProductDao with PhantomDao
{
  self =>

  object products extends PhantomProductTable with Connector

  override def insert(product: Product)(implicit executionContext: ExecutionContext): Future[Product] =
    for {
      resultsSet <- products.store(product).future()
    }
    yield product

  override def getById(id: String)(implicit executionContext: ExecutionContext): OptionT[Future, Product] =
    OptionT {
      for {
        results <- find(_.id is id)
      }
      yield results.headOption
    }

  def find[HL <: HList](query: PhantomProductTable => QueryCondition[HL]): Future[List[Product]] =
    products.select.where(query).fetch()

  override def init(): Future[Seq[ResultSet]] = products.create.ifNotExists().future()

  override def onShutdown(): Unit = shutdown()

  PhantomDao.initialize(self, applicationLifecycle)
}
