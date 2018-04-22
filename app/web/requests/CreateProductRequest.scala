package web.requests

import play.api.libs.json.{Json, OFormat}

case class CreateProductRequest(
      id: String,
      name: String,
      label: Option[String],
      description: Option[String],
      tags: Option[List[String]],
      imageUrls: Option[List[String]]
)

object CreateProductRequest
{
  implicit def oFormat: OFormat[CreateTagRequest] = Json.format[CreateTagRequest]
}