package services

import daos.TagDao
import exceptions.DuplicateItemException
import exceptions.EmptyResultException._
import javax.inject.{Inject, Singleton}
import models.ProductTag
import org.joda.time.DateTime
import scalaz.std.scalaFuture.futureInstance
import utils.GeneralUtils.uuid
import utils.TypeAliases.TagName
import web.requests.CreateTagRequest

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TagService @Inject()(tagDao: TagDao)
{
  import TagService._

  def newTag(createTagRequest: CreateTagRequest)(implicit executionContext: ExecutionContext): Future[ProductTag] =
    for {
      isEmpty <- tagDao.findByName(createTagRequest.name).isEmpty

      productTag <-
        if (isEmpty) tagDao.insert(toProductTag(createTagRequest)) else Future.failed(DuplicateItemException[ProductTag](createTagRequest.name))
    }
    yield productTag

  def getByName(tagName: TagName)(implicit executionContext: ExecutionContext): Future[ProductTag] =
    for {
      productTag <-
        tagDao.findByName(tagName)
          .getOrElseF {
            Future.failed(noTagsWithName(tagName))
          }
    }
    yield productTag
}

object TagService
{
  def toProductTag(createTagRequest: CreateTagRequest): ProductTag =
    ProductTag(
      uuid(), DateTime.now(), createTagRequest.name, createTagRequest.label.getOrElse(createTagRequest.name), createTagRequest.description
    )
}