package exceptions

import utils.TypeAliases.TagName

case class EmptyResultException(description: String) extends Exception
{
  override def getMessage: String = description
}

object EmptyResultException
{
  def noTagsWithName(tagName: TagName) =
    EmptyResultException(s"""Unable to find a tag named "$tagName".""")

  def noProductsWithId(productId: String) =
    EmptyResultException(s"""Unable to find a product with ID "$productId".""")
}
