package web.responses

import play.api.libs.json.{JsObject, JsValue, Json, Writes}

case class ErrorResponse(exception: Exception)

object ErrorResponse
{
  val EXCEPTION_JSON_FIELD = "errorMessage"

  def unapply(jsValue: JsValue): Option[String] =
    for {
      jsObject <- jsValue.asOpt[JsObject]

      errorMessage <- (jsObject \ EXCEPTION_JSON_FIELD).asOpt[String]
    }
    yield errorMessage

  implicit def errorWrite: Writes[ErrorResponse] =
  {
    case ErrorResponse(exception) => Json.obj(EXCEPTION_JSON_FIELD -> exception.getMessage)
  }

}