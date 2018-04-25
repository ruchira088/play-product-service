package web.controllers

import javax.inject._
import models.ServiceInformation
import play.api.libs.json.Json.toJson
import play.api.mvc._
import web.utils.JsonUtils._

@Singleton
class HomeController @Inject()(controllerComponents: ControllerComponents)
  extends AbstractController(controllerComponents)
{
  def info() = Action {
    Ok(toJson(ServiceInformation()))
  }
}