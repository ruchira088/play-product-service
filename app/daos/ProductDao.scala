package daos

import models.Product
import scalaz.OptionT

import scala.concurrent.{ExecutionContext, Future}

trait ProductDao
{
  def insert(product: Product)(implicit executionContext: ExecutionContext): Future[Product]

  def getById(id: String)(implicit executionContext: ExecutionContext): OptionT[Future, Product]
}