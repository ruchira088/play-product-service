package utils

import scala.concurrent.Future
import scala.util.{Failure, Success, Try}
import scala.language.implicitConversions

object ScalaUtils
{
  def toTry[A](option: Option[A], exception: => Exception): Try[A] =
    option.fold[Try[A]](Failure(exception))(Success(_))

  def predicate(condition: Boolean, exception: => Exception): Future[Unit] =
    if (condition) Future.successful((): Unit) else Future.failed(exception)

  def flatten[A](option: Option[List[A]]): List[A] = option.toList.flatten

  implicit def toList[A](item: A): List[A] = List(item)
}
