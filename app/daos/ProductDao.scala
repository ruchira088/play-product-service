package daos

import models.Product
import scalaz.OptionT
import utils.TypeAliases._

import scala.concurrent.{ExecutionContext, Future}

trait ProductDao
{
  def insert(product: Product)(implicit executionContext: ExecutionContext): Future[Product]

  def getById(id: String)(implicit executionContext: ExecutionContext): OptionT[Future, Product]

  def getByTag(tagName: TagName)(implicit executionContext: ExecutionContext): Future[List[Product]]
}