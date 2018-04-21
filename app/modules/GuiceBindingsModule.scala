package modules

import com.google.inject.AbstractModule
import daos.TagDao
import daos.slick.SlickTagDao

class GuiceBindingsModule extends AbstractModule
{
  override def configure(): Unit =
  {
    bind(classOf[TagDao]).to(classOf[SlickTagDao])

  }
}
