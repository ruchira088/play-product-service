package models

import org.joda.time.DateTime

case class ProductTag(
      id: String,
      createdAt: DateTime,
      name: String,
      label: String,
      description: Option[String]
)