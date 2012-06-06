package controllers

import play.api._
import play.api.mvc._
import play.libs.Json._
import models._

object Application extends Controller {
  
  def index = Action {
    //Ok(views.html.index(decomps))
    Ok(views.html.index("hi"))
  }

  def submitCode = Action(parse.json) { request =>
    (request.body \ "code").asOpt[String].map { code =>
      val helper = new Disassembler()
      val diss = helper.disassemble(code)
      Ok("{\"code\":\"" + diss + "\"}")
    }.getOrElse {
      BadRequest("Missing parameter [code]")
    }
  }
  
}
