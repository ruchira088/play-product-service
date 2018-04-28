package daos

import models.ProductTag
import scalaz.OptionT

import scala.concurrent.{ExecutionContext, Future}

trait TagDao
{
  def insert(productTag: ProductTag)(implicit executionContext: ExecutionContext): Future[ProductTag]

  def findByName(name: String)(implicit executionContext: ExecutionContext): OptionT[Future, ProductTag]
}
