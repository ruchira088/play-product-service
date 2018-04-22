package exceptions

object EmptyRouteResultException extends Exception
{
  def emptyRouteResult() = throw EmptyRouteResultException
}
