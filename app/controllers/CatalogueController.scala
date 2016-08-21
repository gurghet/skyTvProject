package controllers

import javax.inject._

import models.product.{Product, ProductRepository}
import play.api.libs.json.{Json, Writes}
import play.api.mvc._

import scala.concurrent.ExecutionContext

@Singleton
class CatalogueController @Inject()(productRepository: ProductRepository)(implicit exec: ExecutionContext)
  extends Controller {
  def fetchProductsAvailableIn(locationID: String) = Action.async {
    productRepository.findByAvailability(locationID) map { availableProducts =>
      Ok(Json.toJson(availableProducts))
    }
  }
}
