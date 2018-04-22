package web.controllers

import com.eed3si9n.ruchij.BuildInfo
import matchers.JsonMatchers._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.http.ContentTypes
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting
{
  "HomeController" should {

    "GET /info" should {

      val request = FakeRequest(GET, "/info")

      "render service information from a new instance of controller" in {
        val controller = new HomeController(stubControllerComponents())
        val home = controller.info().apply(request)

        status(home) mustBe OK
        contentType(home) mustBe Some(ContentTypes.JSON)
        contentAsJson(home) must containJson(Json.obj("serviceName" -> BuildInfo.name))
      }

      "render service information from the application" in {
        val controller = inject[HomeController]
        val home = controller.info().apply(request)

        status(home) mustBe OK
        contentType(home) mustBe Some(ContentTypes.JSON)
        contentAsJson(home) must containJson(Json.obj("serviceName" -> BuildInfo.name))
      }

      "render the index page from the router" in {
        val home = route(app, request).get

        status(home) mustBe OK
        contentType(home) mustBe Some(ContentTypes.JSON)
        contentAsJson(home) must containJson(Json.obj("serviceName" -> BuildInfo.name))
      }
    }
  }
}
