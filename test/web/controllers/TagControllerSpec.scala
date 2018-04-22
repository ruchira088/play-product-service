package web.controllers

import exceptions.EmptyRouteResultException.emptyRouteResult
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._

class TagControllerSpec extends PlaySpec with GuiceOneAppPerTest
{
  "TagController" should {

    "POST /tags" should {

      "create a product tag" in {

        val request = FakeRequest(POST, "/tags").withBody(Json.obj("name" -> "apple"))

        val response = route(app, request).getOrElse(emptyRouteResult())

        println(contentAsString(response))

        println(contentAsString(route(app, FakeRequest(GET, "/tags/apple")).get))
      }
    }
  }

}
