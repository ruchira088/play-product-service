package web.utils

import exceptions.{AggregatedException, DuplicateItemException, EmptyResultException}
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
import play.api.mvc.Result
import play.api.mvc.Results._
import web.responses.ErrorResponse
import utils.ScalaUtils.toList

import scala.concurrent.{ExecutionContext, Future}

object ResponseUtils
{
  private def jsonErrorResponse(throwables: List[Throwable]): JsValue = toJson(ErrorResponse(throwables))

  def handleExceptions(block: => Future[Result])(implicit executionContext: ExecutionContext): Future[Result] =
    block.recover {
      case ex: DuplicateItemException[_] => Conflict(jsonErrorResponse(ex))
      case ex: EmptyResultException => NotFound(jsonErrorResponse(ex))
      case AggregatedException(throwables) => UnprocessableEntity(jsonErrorResponse(throwables))
      case _ => InternalServerError(jsonErrorResponse(new Exception("Something was wrong with the server.")))
    }
}
