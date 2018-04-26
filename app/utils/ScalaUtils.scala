package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

object ScalaUtils
{
  def toTry[A](option: Option[A], exception: => Exception): Try[A] =
    option.fold[Try[A]](Failure(exception))(Success(_))

  def predicate[A](condition: Boolean, onTrue: => Future[A], exception: => Exception): Future[A] =
    if (condition) onTrue else Future.failed(exception)

  def flatten[A](option: Option[List[A]]): List[A] = option.toList.flatten
}
