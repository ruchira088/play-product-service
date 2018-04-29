package matchers

import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.libs.json.{JsObject, JsValue}
import web.responses.ErrorResponse

object JsonMatchers
{
  def containJson(jsObject: JsObject): Matcher[JsValue] =
    {
      case JsObject(json) =>
        MatchResult(
          jsObject.fields.forall { kv => json.toList.contains(kv) },
          s"""Does NOT contain: ${ jsObject.fields.filter { kv => !json.toList.contains(kv) }.toMap }
             |Actual JSON: $json
           """.stripMargin,
          "Contains all the key values"
        )

      case _ => MatchResult(matches = false, "Not an instance of JsObject", "N/A")
    }

  def equalJsValue(expected: JsValue): Matcher[JsValue] =
    (actual: JsValue) => MatchResult(
      expected.equals(actual),
      s"$expected != $actual",
      "JsValues match"
    )

  def equalErrorMessage(exception: Exception): Matcher[JsValue] =
  {
    case ErrorResponse(errorMessage :: _) =>
      MatchResult(
        errorMessage == exception.getMessage,
        s"$errorMessage != ${exception.getMessage}",
        "Exception messages match"
      )

    case _ => MatchResult(matches = false, "Not an error response", "N/A")
  }
}
