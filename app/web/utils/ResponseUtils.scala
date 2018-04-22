package web.utils

import exceptions.DuplicateTagException
import play.api.libs.json.{Json, Writes}
import play.api.libs.json.Json.toJson
import play.api.mvc.{Result, Results}
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

object ResponseUtils
{
  case class ErrorResponse(exception: Exception)

  object ErrorResponse
  {
    implicit def errorWrite: Writes[ErrorResponse] =
    {
      case ErrorResponse(exception) => Json.obj("errorMessage" -> exception.getMessage)
    }
  }

  def handleExceptions(block: => Future[Result])(implicit executionContext: ExecutionContext): Future[Result] =
    block.recover {
      case exception: DuplicateTagException => Conflict(toJson(ErrorResponse(exception)))
    }

}
