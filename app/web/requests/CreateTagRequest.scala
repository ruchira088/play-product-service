package web.requests

import models.ProductTag
import org.joda.time.DateTime
import play.api.libs.json.{Json, OFormat}
import utils.GeneralUtils.uuid

case class CreateTagRequest(
      name: String,
      label: Option[String],
      description: Option[String]
)

object CreateTagRequest
{
  implicit def createTagRequest: OFormat[CreateTagRequest] = Json.format[CreateTagRequest]

  def toProductTag(createTagRequest: CreateTagRequest): ProductTag =
    ProductTag(
      uuid(), DateTime.now(), createTagRequest.name, createTagRequest.label, createTagRequest.description
    )
}