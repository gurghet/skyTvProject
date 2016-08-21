package models.product

import play.api.libs.json.{Json, Writes}

case class Product(uuid: String, tinyDescription: String, category: String)

object Product {
  implicit val productWrites = new Writes[Product] {
    def writes(product: Product) = Json.obj(
      "uuid" -> product.uuid,
      "tiny_description" -> product.tinyDescription,
      "category" -> product.category
    )
  }
}