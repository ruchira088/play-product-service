package daos

import models.ProductTag
import scalaz.OptionT

import scala.concurrent.Future

trait TagDao
{
  def insert(productTag: ProductTag): Future[ProductTag]

  def findByName(name: String): OptionT[Future, ProductTag]
}
