package daos.phantom.tables

import com.outworkers.phantom.Table
import com.outworkers.phantom.keys.{PartitionKey, PrimaryKey}
import models.ProductTag

abstract class PhantomProductTagTable extends Table[PhantomProductTagTable, ProductTag]
{
  object id extends StringColumn with PrimaryKey

  object createdAt extends DateTimeColumn

  object name extends StringColumn with PartitionKey

  object label extends OptionalStringColumn

  object description extends OptionalStringColumn
}