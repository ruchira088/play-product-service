package exceptions

case class AggregatedException(exceptions: List[Throwable]) extends Exception

object AggregatedException
{
  def fromEitherList(throwables: List[Either[Throwable, _]]): AggregatedException =
    AggregatedException {
      throwables.foldLeft(List.empty[Throwable]) {
        case (throwableList, Left(throwable)) => throwableList :+ throwable
        case (throwableList, _) => throwableList
      }
    }

}
