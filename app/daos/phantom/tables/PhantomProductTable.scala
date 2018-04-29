package daos.phantom.tables

import com.outworkers.phantom.Table
import com.outworkers.phantom.keys.{PartitionKey, PrimaryKey}
import models.Product

abstract class PhantomProductTable extends Table[PhantomProductTable, Product]
{
  object id extends StringColumn with PartitionKey

  object createdAt extends DateTimeColumn with PrimaryKey

  object name extends StringColumn

  object label extends StringColumn

  object description extends OptionalStringColumn

  object tags extends ListColumn[String]

  object imageUrls extends ListColumn[String]
}
