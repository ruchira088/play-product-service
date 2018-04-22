package web.utils

import exceptions.{DuplicateTagException, EmptyResultException}
import play.api.libs.json.Json.toJson
import play.api.libs.json.{JsValue, Json, Writes}
import play.api.mvc.Result
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

object ResponseUtils
{
  private case class ErrorResponse(exception: Exception)

  private implicit def errorWrite: Writes[ErrorResponse] =
  {
    case ErrorResponse(exception) => Json.obj("errorMessage" -> exception.getMessage)
  }

  private def jsonErrorResponse(exception: Exception): JsValue = toJson(ErrorResponse(exception))

  def handleExceptions(block: => Future[Result])(implicit executionContext: ExecutionContext): Future[Result] =
    block.recover {
      case ex: DuplicateTagException => Conflict(jsonErrorResponse(ex))
      case ex: EmptyResultException => NotFound(jsonErrorResponse(ex))
      case _ => InternalServerError(jsonErrorResponse(new Exception("Something was wrong with the server.")))
    }

}
