import com.google.inject.{AbstractModule, Provides}
import java.time.Clock
import javax.inject.Inject

import models.product.ProductRepository
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.ws.WSClient
import services.{CatalogueService, CustomerLocationService}


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
  }

  @Provides
  def provideProductRepository: ProductRepository = new ProductRepository

  @Provides
  def provideCatalogueService: CatalogueService = new CatalogueService

  @Provides
  def customerLocationService: CustomerLocationService = new CustomerLocationService

}
