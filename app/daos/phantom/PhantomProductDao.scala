package daos.phantom

import com.outworkers.phantom.builder.clauses.QueryCondition
import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl._
import daos.ProductDao
import daos.phantom.tables.PhantomProductTable
import javax.inject.{Inject, Singleton}
import play.api.inject.ApplicationLifecycle
import scalaz.OptionT
import models.Product
import shapeless.HList
import utils.ConfigUtils.terminate

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class PhantomProductDao @Inject()(connection: CassandraConnection, applicationLifecycle: ApplicationLifecycle)
                                 (implicit executionContext: ExecutionContext)
  extends Database[PhantomProductDao](connection) with ProductDao
{
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

  def init(): Future[Seq[ResultSet]] = products.create.ifNotExists().future()

  init().onComplete {

    case Success(_) => println(classOf[PhantomProductDao].getSimpleName + " has been successfully initialized.")

    case Failure(throwable) => terminate(throwable)
  }
}
