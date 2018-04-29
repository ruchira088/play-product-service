package services

import daos.ProductDao
import exceptions.AggregatedException
import exceptions.EmptyResultException.noProductsWithId
import javax.inject.{Inject, Singleton}
import models.Product
import org.joda.time.DateTime
import utils.ScalaUtils.{flatten, predicate}
import utils.TypeAliases.TagName
import web.requests.CreateProductRequest

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}
import scalaz.std.scalaFuture.futureInstance

@Singleton
class ProductService @Inject()(productDao: ProductDao, tagService: TagService)
{
  import ProductService.toProduct

  def create(createProductRequest: CreateProductRequest)(implicit executionContext: ExecutionContext): Future[Product] =
    for {
      tagsLookup <- Future.sequence {
        flatten(createProductRequest.tags)
          .map {
            tagName => tagService.getByName(tagName).transform {
              case Success(productTag) => Success(Right(productTag))
              case Failure(throwable) => Success(Left(throwable))
            }
          }
      }

      _ <- predicate(tagsLookup.forall(_.isRight), AggregatedException.fromEitherList(tagsLookup))

      product <- productDao.insert(toProduct(createProductRequest))
    }
    yield product


  def getById(productId: String)(implicit executionContext: ExecutionContext): Future[Product] =
    productDao.getById(productId).getOrElseF(Future.failed(noProductsWithId(productId)))

  def getByTag(tagName: TagName)(implicit executionContext: ExecutionContext): Future[List[Product]] =
    productDao.getByTag(tagName)
}

object ProductService
{
  def toProduct: PartialFunction[CreateProductRequest, Product] =
  {
    case CreateProductRequest(id, name, label, description, tags, imageUrls) =>
      Product(id, DateTime.now(), name, label.getOrElse(name), description, flatten(tags).toSet, flatten(imageUrls).toSet)
  }
}
