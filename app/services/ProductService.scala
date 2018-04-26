package services

import daos.ProductDao
import exceptions.AggregatedException
import javax.inject.{Inject, Singleton}
import models.{Product, ProductTag}
import org.joda.time.DateTime
import utils.GeneralUtils.uuid
import utils.ScalaUtils.flatten
import web.requests.CreateProductRequest

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

@Singleton
class ProductService @Inject()(productDao: ProductDao, tagService: TagService)(implicit executionContext: ExecutionContext)
{
  import ProductService.toProduct

  def create(createProductRequest: CreateProductRequest): Future[Product] =
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

      _ <- if (tagsLookup.forall(_.isRight)) Future.successful((): Unit) else Future.failed(AggregatedException.fromEitherList(tagsLookup))

      product <- productDao.insert(toProduct(createProductRequest))
    }
    yield product
}

object ProductService
{
  def toProduct: PartialFunction[CreateProductRequest, Product] =
  {
    case CreateProductRequest(id, name, label, description, tags, imageUrls) =>
      Product(id, DateTime.now(), name, label.getOrElse(name), description, flatten(tags), flatten(imageUrls))
  }
}
