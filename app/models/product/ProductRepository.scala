package models.product

import play.api.Play
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig}
import slick.driver.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class ProductRepository (implicit exec: ExecutionContext) extends HasDatabaseConfig[JdbcProfile] {

  protected lazy val dbConfig = DatabaseConfigProvider.get[JdbcProfile](Play.current)
  import dbConfig.driver.api._

  class ProductsTable(tag: Tag) extends Table[(String, String, String, String)](tag, "product") {
    def uuid             = column[String]("uuid")
    def tinyDescription  = column[String]("tiny_description")
    def availabilityAcc  = column[String]("availability_acc")
    def category         = column[String]("category")
    def * = (uuid, tinyDescription, availabilityAcc, category)
  }

  private val productTableQ: TableQuery[ProductsTable] = TableQuery[ProductsTable]

  def findByAvailability(locationId: String): Future[Seq[Product]] = {
    db.run(productTableQ.filter{ product =>
      List(
        product.availabilityAcc like s"%$locationId%",
        product.availabilityAcc like "%WORLD%"
      ).reduceLeft(_ || _)}.result) map { rawProducts =>
      rawProducts map { case (uuid, tinyDescription, _, category) =>
          Product(uuid, tinyDescription, category)
      }
    }
  }
}