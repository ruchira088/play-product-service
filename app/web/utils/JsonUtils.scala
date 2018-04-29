package web.utils

import models.{Product, ProductTag, ServiceInformation}
import org.joda.time.DateTime
import play.api.libs.json._

import scala.util.Try

object JsonUtils
{
  implicit def jodaTimeJsonFormat: Format[DateTime] = new Format[DateTime]
  {
    override def reads(json: JsValue): JsResult[DateTime] =
      json match {
        case JsString(dateTimeString) =>
          Try(DateTime.parse(dateTimeString))
            .fold(throwable => JsError(throwable.getMessage), JsSuccess(_))
        case _ => JsError()
      }

    override def writes(dateTime: DateTime): JsValue = JsString(dateTime.toString)
  }

  implicit def serviceInfoJsonFormat: OFormat[ServiceInformation] = Json.format[ServiceInformation]

  implicit def productTagJsonFormat: OFormat[ProductTag] = Json.format[ProductTag]

  implicit def productJsonFormat: OFormat[Product] = Json.format[Product]

}
