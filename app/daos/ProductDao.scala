package daos

import models.Product
import scalaz.OptionT

import scala.concurrent.Future

trait ProductDao
{
  def insert(product: Product): Future[Product]

  def getById(id: String): OptionT[Future, Product]
}