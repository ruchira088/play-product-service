package exceptions

case class ValidationException(validationError: String) extends Exception
