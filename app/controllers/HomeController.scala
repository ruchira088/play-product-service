package controllers

import controllers.utils.JsonUtils._
import javax.inject._
import models.ServiceInformation
import play.api.libs.json.Json.toJson
import play.api.mvc._

@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents)
  extends AbstractController(controllerComponents)
{
  def info() = Action {
    Ok(toJson(ServiceInformation()))
  }
}
