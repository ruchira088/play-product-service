package exceptions

case class EmptyResultException(description: String) extends Exception
{
  override def getMessage: String = description
}

object EmptyResultException
{
  def noTagsWithName(tagName: String) =
    EmptyResultException(s"""Unable to find a tag named "$tagName".""")
}
