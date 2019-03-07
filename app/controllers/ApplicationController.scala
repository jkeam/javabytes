package controllers

import javax.inject._
import play.api.mvc._
import models._


@Singleton
class ApplicationController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) {

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
