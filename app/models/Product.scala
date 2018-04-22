package models

import org.joda.time.DateTime

case class Product(
      id: String,
      createdAt: DateTime,
      name: String,
      label: Option[String],
      description: Option[String],
      tags: Option[List[String]],
      imageUrls: Option[List[String]]
)
