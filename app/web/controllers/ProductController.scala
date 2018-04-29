package web.controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.JsValue
import play.api.libs.json.Json.toJson
import play.api.mvc._
import services.ProductService
import web.requests.CreateProductRequest
import web.utils.RequestUtils.extractF
import web.utils.ResponseUtils.handleExceptions
import web.utils.JsonUtils._

import scala.concurrent.ExecutionContext

@Singleton
class ProductController @Inject()(controllerComponents: ControllerComponents, parser: PlayBodyParsers, productService: ProductService)(implicit executionContext: ExecutionContext)
  extends AbstractController(controllerComponents)
{
  def create(): Action[JsValue] = Action.async(parser.json) {
    implicit jsonRequest: Request[JsValue] =>
      handleExceptions {
        for {
          createProductRequest <- extractF[CreateProductRequest]
          product <- productService.create(createProductRequest)
        }
        yield Ok(toJson(product))
      }
  }

//  def getById(productId: String) =
//    Action.async {
//      handleExceptions {
//        productService.getById()
//      }
//    }
}
