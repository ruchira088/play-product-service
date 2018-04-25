package exceptions

import scala.reflect.ClassTag

case class DuplicateItemException[T](itemName: String)(implicit classTag: ClassTag[T]) extends Exception
{
  override def getMessage: String =
    s"""${classTag.runtimeClass.getSimpleName} named "$itemName" already exists in the database."""
}