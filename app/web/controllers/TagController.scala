package web.controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
import play.api.mvc._
import services.TagService
import web.requests.CreateTagRequest
import web.utils.JsonUtils._
import web.utils.RequestUtils.extractF
import web.utils.ResponseUtils.handleExceptions

import scala.concurrent.ExecutionContext

@Singleton
class TagController @Inject()(
       controllerComponents: ControllerComponents,
       parser: PlayBodyParsers,
       tagService: TagService)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create(): Action[JsValue] = Action.async(parser.json) {
    implicit jsonRequest => handleExceptions {
      for {
        createTagRequest <- extractF[CreateTagRequest]
        productTag <- tagService.newTag(createTagRequest)
      }
      yield Ok(toJson(productTag))
    }
  }

  def getByName(name: String): Action[AnyContent] =
    Action.async {
      handleExceptions {
        for {
          productTag <- tagService.getByName(name)
        }
        yield Ok(toJson(productTag))
      }
    }
}
