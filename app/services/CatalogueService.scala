package services

import javax.inject.Inject

import controllers.CatalogueController
import models.product._

import scala.concurrent.{ExecutionContext, Future}

class CatalogueService (implicit exec: ExecutionContext) {
  def getAvailableProducts(maybeLocationID: Option[String]): Future[Seq[Product]] = {
    // here would be some logic to connect to the product repository via HTTP
    // through the CatalogueController, or maybe via a remote akka actor
    // in this implementation this is simulated by just calling the product repository
    val productRepository = new ProductRepository
    productRepository.findByAvailability(maybeLocationID.getOrElse("WORLD"))
  }
}
