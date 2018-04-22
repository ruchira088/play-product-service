package matchers

import org.scalatest.matchers.{MatchResult, Matcher}
import play.api.libs.json.{JsObject, JsValue}

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
          "Contains all the key values."
        )

      case _ => MatchResult(matches = false, "Not an instance of JsObject", "N/A")
    }
}
