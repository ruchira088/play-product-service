# product_tags schema

# ---!Ups

CREATE TABLE product_tags(
  id VARCHAR(36) NOT NULL PRIMARY KEY,
  created_at TIMESTAMP NOT NULL,
  name VARCHAR(255) NOT NULL ,
  label VARCHAR(255),
  description TEXT
)

# ---!Downs

DROP TABLE product_tags
