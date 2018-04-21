package exceptions

case class EnvValueUndefinedException(name: String) extends Exception
{
  override def getMessage: String = s""""$name" is NOT defined as an environment variable."""
}
