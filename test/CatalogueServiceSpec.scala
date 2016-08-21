import models.product.ProductRepository
import play.api.Application
import play.api.test._

object CatalogueServiceSpec extends PlaySpecification {

  "Product repository" should {

    def prodRepo(implicit app: Application) = Application.instanceCache[ProductRepository].apply(app)

    "return 3 results when locationID is LIVERPOOL" in new WithApplication() {
      val result = await(prodRepo.findByAvailability("LIVERPOOL"))
      result.length === 3
    }

    "return Liverpool TV when locationID is LIVERPOOL" in new WithApplication() {
      val result = await(prodRepo.findByAvailability("LIVERPOOL"))
      result.exists(_.uuid == "Liverpool TV") must beTrue
    }

    "return Sky News and Sky Sport News when locationID is LIVERPOOL" in new WithApplication() {
      val result = await(prodRepo.findByAvailability("LIVERPOOL"))
      result.exists(_.uuid == "Sky News") must beTrue
      result.exists(_.uuid == "Sky Sport News") must beTrue
    }

    "return 2 results when locationID is WORLD" in new WithApplication() {
      val result = await(prodRepo.findByAvailability("WORLD"))
      result.length === 2
    }

    "Sky News and Sky Sport News should always be returned" in new WithApplication() {
      val result = await(prodRepo.findByAvailability("NONSENSE"))
      result.exists(_.uuid == "Sky News") must beTrue
      result.exists(_.uuid == "Sky Sport News") must beTrue
    }

  }
}
