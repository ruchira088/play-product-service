package controllers.utils

import models.ServiceInformation
import play.api.libs.json._

object JsonUtils
{
  implicit def serviceInfoJsonFormat: OFormat[ServiceInformation] = Json.format[ServiceInformation]
}
