package web.controllers

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

        val response = route(app, request).get

        println(contentAsString(response))
      }
    }
  }

}
