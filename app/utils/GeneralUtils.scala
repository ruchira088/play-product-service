package utils

import java.util.UUID

object GeneralUtils
{
  def uuid(): String = UUID.randomUUID().toString
}
