package web.responses

import play.api.libs.json._

case class ErrorResponse(throwables: List[Throwable])

object ErrorResponse
{
  val EXCEPTION_JSON_FIELD = "errorMessages"

  def unapply(jsValue: JsValue): Option[List[String]] =
    for {
      jsObject <- jsValue.asOpt[JsObject]

      errorMessages <- (jsObject \ EXCEPTION_JSON_FIELD).asOpt[List[String]]
    }
    yield errorMessages

  implicit def errorWrite: Writes[ErrorResponse] =
  {
    case ErrorResponse(throwables) =>
      Json.obj(EXCEPTION_JSON_FIELD -> JsArray { throwables.map(throwable => JsString(throwable.getMessage)) })
  }

}