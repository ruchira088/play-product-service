package services

import daos.TagDao
import exceptions.{DuplicateTagException, EmptyResultException}
import javax.inject.{Inject, Singleton}
import models.ProductTag
import org.joda.time.DateTime
import utils.GeneralUtils.uuid
import web.requests.CreateTagRequest

import scala.concurrent.{ExecutionContext, Future}
import scalaz.std.scalaFuture.futureInstance

@Singleton
class TagService @Inject()(tagDao: TagDao)(implicit executionContext: ExecutionContext)
{
  import TagService._

  def newTag(createTagRequest: CreateTagRequest): Future[ProductTag] =
    for {
      isEmpty <- tagDao.findByName(createTagRequest.name).isEmpty

      productTag <-
        if (isEmpty) tagDao.insert(toProductTag(createTagRequest)) else Future.failed(DuplicateTagException(createTagRequest.name))
    }
    yield productTag

  def getByName(tagName: String): Future[ProductTag] =
    for {
      productTag <-
        tagDao.findByName(tagName)
          .getOrElseF {
            Future.failed(EmptyResultException(s"""Unable to find a tag named "$tagName""""))
          }
    }
    yield productTag
}

object TagService
{
  def toProductTag(createTagRequest: CreateTagRequest): ProductTag =
    ProductTag(
      uuid(), DateTime.now(), createTagRequest.name, createTagRequest.label, createTagRequest.description
    )
}