package utils

import scala.util.{Failure, Success, Try}

object ScalaUtils
{
  def toTry[A](option: Option[A], exception: => Exception): Try[A] =
    option.fold[Try[A]](Failure(exception))(Success(_))
}
