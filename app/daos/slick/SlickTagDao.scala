package daos.slick

import daos.TagDao
import javax.inject.{Inject, Singleton}
import models.ProductTag
import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import scalaz.OptionT
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class SlickTagDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with SlickMappedColumns with TagDao
{
  import SlickTagDao.TABLE_NAME
  import profile.api._

  class TagTable(tag: Tag) extends Table[ProductTag](tag, TABLE_NAME)
  {
    def id = column[String]("id")
    def createdAt = column[DateTime]("created_at")
    def name = column[String]("name")
    def label = column[Option[String]]("label")
    def description = column[Option[String]]("description")

    def pk = primaryKey("pk_product_tags", id)

    override def * : ProvenShape[ProductTag] =
      (id, createdAt, name, label, description) <> (ProductTag.tupled, ProductTag.unapply)
  }

  val tags = TableQuery[TagTable]

  override def insert(productTag: ProductTag)(implicit executionContext: ExecutionContext): Future[ProductTag] =
    db.run(tags += productTag).map(_ => productTag)

  override def findByName(name: String)(implicit executionContext: ExecutionContext): OptionT[Future, ProductTag] =
    OptionT {
      db.run {
        tags
          .filter(_.name === name)
          .sortBy(_.createdAt.desc)
          .result
          .headOption
      }
    }
}

object SlickTagDao
{
  val TABLE_NAME = "product_tags"
}