package exceptions

import play.api.libs.json.{JsPath, JsonValidationError}

case class RequestBodyDeserializationException(errors: List[(JsPath, List[JsonValidationError])]) extends Exception

object RequestBodyDeserializationException
{
  def create(errors: Seq[(JsPath, Seq[JsonValidationError])]): RequestBodyDeserializationException =
    RequestBodyDeserializationException {
      errors.toList.map {
        case (jsPath, validationErrors) => jsPath -> validationErrors.toList
      }
    }
}