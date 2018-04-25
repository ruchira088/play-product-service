package exceptions

case class EmptyResultException(description: String) extends Exception
{
  override def getMessage: String = description
}
