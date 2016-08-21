package controllers

import javax.inject.Inject

import models.product.ProductRepository
import play.api.http.MimeTypes
import play.api.mvc.{Action, Controller}

import scalatags.Text.all._
import scala.concurrent.{ExecutionContext, Future}

class MainController @Inject()(implicit exec: ExecutionContext) extends Controller {

  val host = ""

  def confirm = Action { request =>
    val customerID = request.cookies.get("customerID").get.value
    val productUuidList = request.body.asFormUrlEncoded
      .getOrElse(Map.empty[String, Seq[String]])
      .keySet
    Ok(views.html.confirmation(customerID, productUuidList))
  }

  def index = Action {
    Ok(html(
      head(
        link(rel:="stylesheet", href:="/assets/stylesheets/main.css")
      ),
      body(
        header("Header"),
        form(method:="POST",
          div(id:="content",
            div(id:="sports", h3("Sports"), div(ul(
              li(attr("v-for"):="sportsProduct in sports", label(
                input(
                  attr("v-bind:name"):="sportsProduct.uuid",
                  `type`:="checkbox",
                  attr("v-on:click.self"):="select(sportsProduct, $event)"),
                "{{sportsProduct.tiny_description}}"))
            ))),
            div(id:="news", h3("News"), div(ul(
              li(attr("v-for"):="newsProduct in news", label(
                input(
                  attr("v-bind:name"):="newsProduct.uuid",
                  `type`:="checkbox",
                  attr("v-on:click.self"):="select(newsProduct, $event)"),
                "{{newsProduct.tiny_description}}"))
            ))),
            div(id:="basket", h3("Basket"), div(ul(
              li(attr("v-for"):="product in basket", "{{product.tiny_description}}")
            )), div(input(
              id:="confirm",
              `type`:="submit",
              attr("v-bind:disabled"):="basket.length === 0"))),
            div(id:="error", attr("v-bind:show"):="error", "{{error}}")
          )
        ),
        footer("Footer"),
        script(src:="/assets/javascripts/vue.js"),
        script(src:="/assets/javascripts/vue-resource.js"),
        script(raw(
          s"""
            |new Vue({
            | el:'#content',
            | data: {
            |   sports: [],
            |   news: [],
            |   basket: [],
            |   error: ''
            | },
            | created: function () {
            |   this.$$http.get('$host/productSelection')
            |     .then(function (response) {
            |       var result = response.json()
            |       Vue.set(this, 'sports', result.sports)
            |       Vue.set(this, 'news', result.news)
            |     }, response => {
            |       this.error = response.text()
            |     })
            | },
            | methods: {
            |   select: function (product, event) {
            |     if (event.target.checked === true) {
            |       this.basket.push(product)
            |     } else {
            |       var i = this.basket.indexOf(product)
            |       this.basket.splice(i, 1)
            |     }
            |   }
            | }
            |})
          """.stripMargin))
      )
    ).render).as(MimeTypes.HTML)
  }
}
