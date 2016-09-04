package controllers

import play.api._
import play.api.mvc._
import play.libs.Json._
import models._

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index(""))
  }

  def submitCode = Action(parse.json) { request =>
    (request.body \ "code").asOpt[String].map { code =>
      val helper = new Disassembler()
      val diss = helper.disassemble(code)
      Ok(diss)
    }.getOrElse {
      BadRequest("Missing parameter [code]")
    }
  }
  
}
