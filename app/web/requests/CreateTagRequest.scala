package web.requests

import play.api.libs.json.{Json, OFormat}

case class CreateTagRequest(
      name: String,
      label: Option[String],
      description: Option[String]
)

object CreateTagRequest
{
  implicit def oFormat: OFormat[CreateTagRequest] = Json.format[CreateTagRequest]
}