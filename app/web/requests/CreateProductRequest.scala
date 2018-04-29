package web.requests

import play.api.libs.json.{Json, OFormat}
import utils.TypeAliases._

case class CreateProductRequest(
      id: String,
      name: String,
      label: Option[String],
      description: Option[String],
      tags: Option[List[TagName]],
      imageUrls: Option[List[ImageId]]
)

object CreateProductRequest
{
  implicit def oFormat: OFormat[CreateProductRequest] = Json.format[CreateProductRequest]
}