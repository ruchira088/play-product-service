package web.controllers

import daos.TagDao
import javax.inject.{Inject, Singleton}
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
import play.api.mvc.{AbstractController, Action, ControllerComponents, PlayBodyParsers}
import web.requests.CreateTagRequest
import web.requests.CreateTagRequest.toProductTag
import web.utils.RequestUtils.extract
import web.utils.JsonUtils._

import scala.concurrent.ExecutionContext
import scala.concurrent.Future.fromTry

@Singleton
class TagController @Inject()(
       controllerComponents: ControllerComponents,
       parser: PlayBodyParsers,
       tagDao: TagDao)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create(): Action[JsValue] = Action.async(parser.json) {
    implicit jsonRequest =>
      for {
        createTag <- fromTry(extract[CreateTagRequest])
        productTag <- tagDao.insert(toProductTag(createTag))
      }
      yield Ok(toJson(productTag))
  }
}
