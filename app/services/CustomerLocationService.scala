package services

class CustomerLocationService {
  def getLocationIDFromCustomerID(customerID: String): Option[String] = customerID match {
    case "John" => Some("LONDON")
    case "Mallory" => Some("LIVERPOOL")
    case "Diane" => Some("WORLD")
    case _ => Option.empty[String]
  }
}
