package models

import org.joda.time.DateTime
import utils.TypeAliases._

case class Product(
      id: String,
      createdAt: DateTime,
      name: String,
      label: String,
      description: Option[String],
      tags: Set[TagName],
      imageUrls: Set[ImageId]
)