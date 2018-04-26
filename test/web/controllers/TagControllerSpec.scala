package web.controllers

import exceptions.DuplicateItemException
import exceptions.EmptyResultException.noTagsWithName
import exceptions.EmptyRouteResultException.emptyRouteResult
import matchers.JsonMatchers._
import models.ProductTag
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import utils.GeneralUtils.uuid

import scala.concurrent.Future

class TagControllerSpec extends PlaySpec with GuiceOneAppPerTest
{
  "TagController" should {

    "create a product tag" in {

      val tagName = "apple"

      val createTagResponse = createTag(tagName)

      status(createTagResponse) mustBe OK
      contentAsJson(createTagResponse) must containJson(Json.obj("name" -> tagName))

      val getTagResponse = getTag(tagName)

      status(getTagResponse) mustBe OK
      contentAsJson(getTagResponse) must equalJsValue(contentAsJson(createTagResponse))

      val createDuplicateTagResponse = createTag(tagName)

      status(createDuplicateTagResponse) mustBe CONFLICT
      contentAsJson(createDuplicateTagResponse) must equalErrorMessage(DuplicateItemException[ProductTag](tagName))

      val nonExistentTag = uuid()
      val getNonExistentTagResponse = getTag(nonExistentTag)

      status(getNonExistentTagResponse) mustBe NOT_FOUND
      contentAsJson(getNonExistentTagResponse) must equalErrorMessage(noTagsWithName(nonExistentTag))
    }
  }

  def createTag(tagName: String): Future[Result] =
    route(app, FakeRequest(POST, "/tags").withBody(Json.obj("name" -> tagName)))
      .getOrElse(emptyRouteResult())

  def getTag(tagName: String): Future[Result] =
    route(app, FakeRequest(GET, s"/tags/$tagName")).getOrElse(emptyRouteResult())

}
