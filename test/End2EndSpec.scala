import org.scalatest.time.{Seconds, Span}
import org.scalatestplus.play._
import org.scalatest.Matchers._

/**
  * Created by gurghet on 21/08/16.
  */
class End2EndSpec extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with ChromeFactory {
  "Navigating the home page" must {
    "when the customer selects a product, the basket is updated to show the selected products" in {
      go to s"http://localhost:$port/"
      add cookie("customerID", "John")
      go to s"http://localhost:$port/"
      implicitlyWait(Span(1, Seconds))
      click on name("Sky News")
      find(id("basket")).get.text should include("Sky News")
    }

    "when the customer chooses to checkout, the confirmation page shows his name and products" in {
      go to s"http://localhost:$port/"
      add cookie("customerID", "John")
      go to s"http://localhost:$port/"
      implicitlyWait(Span(1, Seconds))
      click on name("Sky News")
      click on id("confirm")
      eventually { pageSource should include("John") }
      pageSource should include("Sky News")
    }
  }
}
