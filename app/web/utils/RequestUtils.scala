package web.utils

import exceptions.RequestBodyDeserializationException
import play.api.libs.json.{JsValue, Reads}
import play.api.mvc.Request

import scala.util.{Failure, Success, Try}

object RequestUtils
{
  def extract[A](implicit jsonRequest: Request[JsValue], reads: Reads[A]): Try[A] =
    jsonRequest.body.validate[A].fold[Try[A]](
      jsonErrors => Failure(RequestBodyDeserializationException.create(jsonErrors)),
      Success(_)
    )
}