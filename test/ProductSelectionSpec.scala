import controllers.ProductSelectionController
import models.product.Product
import org.specs2.mock.Mockito
import play.api.mvc._
import play.api.test.{FakeRequest, PlaySpecification}
import services.{CatalogueService, CustomerLocationService}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ProductSelectionSpec extends PlaySpecification with Results with Mockito {
  "ProductSelectionController" should {
    "present Anonymous with Arsenal TV" in {
      val catalogueServiceMock: CatalogueService = mock[CatalogueService]
      catalogueServiceMock.getAvailableProducts(any) returns
        Future(Seq(Product("Arsenal TV", "Arsenal TV", "Sports")))
      val customerLocationServiceMock: CustomerLocationService = mock[CustomerLocationService]
      customerLocationServiceMock.getLocationIDFromCustomerID(any) returns Some("LONDON")
      val controller = new ProductSelectionController(customerLocationServiceMock, catalogueServiceMock)
      val result: Future[Result] = controller.index().apply(FakeRequest())
      val bodyText: String = contentAsString(result)
      bodyText must not contain """"uuid":"Arsenal TV""""
      bodyText must contain("Oops")
    }

    "present John with Arsenal TV" in {
      val catalogueServiceMock: CatalogueService = mock[CatalogueService]
      catalogueServiceMock.getAvailableProducts(any) returns
        Future(Seq(Product("Arsenal TV", "Arsenal TV", "Sports")))
      val customerLocationServiceMock: CustomerLocationService = mock[CustomerLocationService]
      customerLocationServiceMock.getLocationIDFromCustomerID(any) returns Some("LONDON")
      val controller = new ProductSelectionController(customerLocationServiceMock, catalogueServiceMock)
      val result: Future[Result] = controller.index().apply(FakeRequest().withCookies(Cookie("customerID", "John")))
      val bodyText: String = contentAsString(result)
      bodyText must contain(""""uuid":"Arsenal TV"""")
      bodyText must contain(""""tiny_description":"Arsenal TV"""")
      bodyText must contain(""""category":"Sports"""")
    }
  }
}
