package daos.slick

import java.sql.Timestamp

import org.joda.time.DateTime
import play.api.db.slick.HasDatabaseConfig
import slick.jdbc.JdbcProfile

trait SlickMappedColumns
{
  self: HasDatabaseConfig[JdbcProfile] =>

  import profile.api._

  implicit def jodaTimeMappedColumn: profile.BaseColumnType[DateTime] =
    profile.MappedColumnType.base[DateTime, Timestamp](
      dateTime => new Timestamp(dateTime.getMillis),
      timeStamp => new DateTime(timeStamp.getTime)
    )
}
