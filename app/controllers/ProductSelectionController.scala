package controllers

import javax.inject._

import play.api.libs.ws.WSClient
import play.api.mvc._
import services.{CatalogueService, CustomerLocationService}
import models.product.Product
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductSelectionController @Inject()(customerLocationService: CustomerLocationService,
                                           catalogueService: CatalogueService)(implicit exec: ExecutionContext)
  extends Controller {

  def index = Action.async { request =>
    request.cookies.get("customerID").map { customerIDCookie =>
      val maybeLocationID = customerLocationService.getLocationIDFromCustomerID(customerIDCookie.value)
      val eventualAvailableProducts = catalogueService.getAvailableProducts(maybeLocationID)
      eventualAvailableProducts map { availableProducts =>
        val newsProducts = availableProducts.filter(_.category == "News")
        val sportProducts = availableProducts.filter(_.category == "Sports")
        Ok(Json.toJson(Map("sports" -> sportProducts, "news" -> newsProducts)))
      }
    }.getOrElse {
      Future(Unauthorized("Oops, you are not connected"))
    }
  }
}