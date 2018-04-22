package exceptions

case class DuplicateTagException(tagName: String) extends Exception
